package com.sbs.hrRecommendation.controllers;
import com.sbs.hrRecommendation.models.recommendation;
import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.repositories.userProfileRepository;
import com.sbs.hrRecommendation.repositories.recommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/recommendations")
public class recommendationController {

    @Autowired
    private recommendationRepository recRepository;
    @Autowired
    private userProfileRepository UserProfileRepository;

    //used to get all the recommendations present in db
    @GetMapping
    @RequestMapping("{id}")
    public Map<String, List<recommendation>> list(@PathVariable Long id) {
        List<recommendation> allRecomm = new ArrayList<recommendation>();
        List<recommendation> archived = new ArrayList<recommendation>();
        List<recommendation> myDrafts=new ArrayList<recommendation>();
        List<recommendation> myRecomm=new ArrayList<recommendation>();
        Map<String, List<recommendation>> map =new HashMap<String,List<recommendation>>();
        userProfile userdata = UserProfileRepository.getReferenceById(id);
        if(!UserProfileRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Id does not exist");
        if(userdata.getRoles().equals(userProfile.roles_enum.HR )){
            allRecomm = recRepository.findByIsArchivedAndMyStatusNot(false, recommendation.status.DRAFT);
            archived =recRepository.findByIsArchived(true);
            myDrafts =recRepository.findByUserIdAndMyStatus(id, recommendation.status.DRAFT);
            myRecomm =recRepository.findByUserIdAndIsArchivedAndMyStatusNot(id, false, recommendation.status.DRAFT);
           map.put("allRecommendations",allRecomm);
            map.put("archived",archived);
            map.put("myDrafts",myDrafts);
            map.put("myRecommendations",myRecomm);
        }
        else if (userdata.getRoles().equals(userProfile.roles_enum.USER)){
            allRecomm=recRepository.findByIsArchivedAndMyStatusNotAndIsPrivate(false,recommendation.status.DRAFT,false);
            myDrafts=recRepository.findByUserIdAndMyStatus(id, recommendation.status.DRAFT);
            myRecomm=recRepository.findByUserIdAndIsArchivedAndMyStatusNot(id, false, recommendation.status.DRAFT);
            map.put("allRecommendations",allRecomm);
            map.put("myDrafts",myDrafts);
            map.put("myRecommendations",myRecomm);
        }
        return map;
    }

    @PostMapping
    @RequestMapping("/save")
    public recommendation createDraft(@RequestBody final recommendation Recommendation) {
        Recommendation.setMyStatus(recommendation.status.DRAFT);
        return recRepository.saveAndFlush(Recommendation);

    }
    @PostMapping
    @RequestMapping("/publish")
    public recommendation createPublish(@RequestBody final recommendation Recommendation) {
        Recommendation.setMyStatus(recommendation.status.PENDING);
        return recRepository.saveAndFlush(Recommendation);

    }

    //delete a particular recommendation
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        recommendation rec = recRepository.getOne(id);
        if(!recRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recommendation Id does not exist");
        if (!rec.getMyStatus().equals(recommendation.status.DRAFT))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status should be only draft");
        recRepository.deleteById(id);
    }

    @PutMapping("{id}")
    public recommendation updateRecommendation(@PathVariable Long id, @RequestBody recommendation Recommendation){
        recommendation existingRecommendation = recRepository.getReferenceById(id);
        Long authorId =  existingRecommendation.getUserId();
        Long requestUserId = Recommendation.getUserId();
        userProfile requestUser = UserProfileRepository.getReferenceById(requestUserId);
        userProfile.roles_enum requestUserRole = requestUser.getRoles();
        recommendation.status existingRecommendationStatus = existingRecommendation.getMyStatus();
        LocalDateTime lt = LocalDateTime.now();


        /*
            DRAFT:
            Author should be same as request user and Recommendation should be in DRAFT state
        */
        if(Objects.equals(authorId, requestUserId)
                && Objects.equals(existingRecommendationStatus, recommendation.status.DRAFT)
                && (
                        (Objects.equals(Recommendation.getMyStatus(),recommendation.status.PENDING))
                ||      (Objects.equals(Recommendation.getMyStatus(),recommendation.status.DRAFT))
                    )
        ){
            existingRecommendation.setSubject(Recommendation.getSubject());
            existingRecommendation.setDescription(Recommendation.getDescription());
            existingRecommendation.setIsPrivate(Recommendation.getIsPrivate());
            existingRecommendation.setMyStatus(Recommendation.getMyStatus());
            existingRecommendation.setModifiedAt(lt);
            return recRepository.saveAndFlush(existingRecommendation);
        }


        /*
            CHANGES_REQUEST:
            Author should be same as request user and Recommendation should be in CHANGES_REQUEST state
        */
        if(Objects.equals(authorId, requestUserId)
                && Objects.equals(existingRecommendationStatus, recommendation.status.CHANGES_REQUESTED)){
            existingRecommendation.setSubject(Recommendation.getSubject());
            existingRecommendation.setDescription(Recommendation.getDescription());
            existingRecommendation.setMyStatus(recommendation.status.PENDING);
            existingRecommendation.setModifiedAt(lt);
            return recRepository.saveAndFlush(existingRecommendation);
        }


        /*
            STATUS UPDATE:
            Requested owner should be HR,
            Author should not be same as requested user,
            Recommendation should not be in DRAFT state,
            HR cannot change the status to DRAFT
        */
        if(Objects.equals(requestUserRole, userProfile.roles_enum.HR)
                && !Objects.equals(authorId,requestUserId)
                && !Objects.equals(existingRecommendationStatus, recommendation.status.DRAFT)
                && !Objects.equals(Recommendation.getMyStatus(),null)
                && !Objects.equals(Recommendation.getMyStatus(),recommendation.status.DRAFT)) {
            existingRecommendation.setMyStatus(Recommendation.getMyStatus());
            existingRecommendation.setModifiedAt(lt);
            return recRepository.saveAndFlush(existingRecommendation);
        }


       /*
            ARCHIVED:
            Requested owner should be HR,
            Author should not be same as requested owner,
            Recommendation status should be APPROVED, DECLINED, PENDING
       */

        if(Objects.equals(requestUserRole, userProfile.roles_enum.HR)
                && !Objects.equals(authorId,requestUserId)
                && !Objects.equals(Recommendation.getIsArchived(),null)
                && (Objects.equals(existingRecommendationStatus, recommendation.status.APPROVED)
                ||  Objects.equals(existingRecommendationStatus, recommendation.status.DECLINED)
                || Objects.equals(existingRecommendationStatus, recommendation.status.PENDING))) {
            existingRecommendation.setMyStatus(existingRecommendationStatus);
            existingRecommendation.setIsArchived(Recommendation.getIsArchived());
            existingRecommendation.setModifiedAt(lt);
            return recRepository.saveAndFlush(existingRecommendation);
        }


        // User is not authorised to update draft

       throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operation not valid");
    }
}
