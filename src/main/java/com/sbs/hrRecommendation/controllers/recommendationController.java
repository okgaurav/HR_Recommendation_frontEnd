package com.sbs.hrRecommendation.controllers;
import com.sbs.hrRecommendation.dto.RecommendationResponse;
import com.sbs.hrRecommendation.dto.UpdateRecommendationRequestDTO;
import com.sbs.hrRecommendation.models.recommendation;
import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.payload.response.messageResponse;
import com.sbs.hrRecommendation.repositories.userProfileRepository;
import com.sbs.hrRecommendation.repositories.recommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/recommendations")
public class recommendationController {

    public static final String ARCHIVED = "ARCHIVED";
    public static final String DRAFT = "DRAFT";
    public static final String ALLRECOMMENDATIONS = "ALLRECOMMENDATIONS";
    public static final String MYRECOMMENDATIONS = "MYRECOMMENDATIONS";

    @Autowired
    private recommendationRepository recRepository;
    @Autowired
    private userProfileRepository UserProfileRepository;

    //used to get all the recommendations present in db
    @GetMapping
    @RequestMapping("{id}")
    public ResponseEntity<?> list(@PathVariable Long id) {

     List<RecommendationResponse> allRecomm = new ArrayList<RecommendationResponse>();
        List<RecommendationResponse> archived = new ArrayList<RecommendationResponse>();
        List<RecommendationResponse> myDrafts=new ArrayList<RecommendationResponse>();
        List<RecommendationResponse> myRecomm=new ArrayList<RecommendationResponse>();
        Map<String, List<RecommendationResponse>> map =new HashMap<String,List<RecommendationResponse>>();
        userProfile userdata = UserProfileRepository.getReferenceById(id);
        if(!UserProfileRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Id does not exist");
        if(userdata.getRoles().equals(userProfile.roles_enum.HR )){
            allRecomm = recRepository.findAllHrRecommendations();
            archived =recRepository.findArchived();
            myDrafts =recRepository.findDrafts(id);
            myRecomm =recRepository.findMyRecommendations(id);
           map.put("allRecommendations",allRecomm);
            map.put("archived",archived);
            map.put("myDrafts",myDrafts);
            map.put("myRecommendations",myRecomm);
        }
        else if (userdata.getRoles().equals(userProfile.roles_enum.USER)){
            allRecomm=recRepository.findAllUserRecommendations();
            myDrafts=recRepository.findDrafts(id);
            myRecomm=recRepository.findMyRecommendations(id);
            map.put("allRecommendations",allRecomm);
            map.put("myDrafts",myDrafts);
            map.put("myRecommendations",myRecomm);
        }
        return ResponseEntity.ok(map);
    }

    // Fetch recommendations of a particular category
    @GetMapping
    @RequestMapping(value = "{id}/{category}", method = RequestMethod.GET)
    public List<RecommendationResponse> listRecommendationsCategoryWise(@PathVariable Long id, @PathVariable String category) {
        // If userId is not valid
        if(!UserProfileRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Id does not exist");
        userProfile userdata = UserProfileRepository.getReferenceById(id);
        String categoryTab = category.toUpperCase();

        // USER Role not allowed to search in Archived records
        if (Objects.equals(userdata.getRoles(),userProfile.roles_enum.USER) && Objects.equals(categoryTab,ARCHIVED))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operation not valid");

        // Send a particular category of recommendations upon user role access
        switch (categoryTab) {
            case ARCHIVED:
                return recRepository.findArchived();
            case DRAFT:
                return recRepository.findDrafts(id);
            case MYRECOMMENDATIONS:
                return recRepository.findMyRecommendations(id);
            case ALLRECOMMENDATIONS:
                if (userdata.getRoles().equals(userProfile.roles_enum.USER))
                    return recRepository.findAllUserRecommendations();
                else
                    return recRepository.findAllHrRecommendations();
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operation not valid");
        }
    }


    @GetMapping
    @RequestMapping(value = "/rec/{id}", method = RequestMethod.GET)
    public RecommendationResponse get(@PathVariable Long id){
        if(!recRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recommendation Id does not exist");
        return recRepository.findRecommendation(id);
    }

    @PostMapping
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public recommendation createDraft(@RequestBody final recommendation Recommendation) {
        Recommendation.setMyStatus(recommendation.status.DRAFT);
        return recRepository.saveAndFlush(Recommendation);

    }
    @PostMapping
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public recommendation createPublish(@RequestBody final recommendation Recommendation) {
        Recommendation.setMyStatus(recommendation.status.PENDING);
        return recRepository.saveAndFlush(Recommendation);

    }

    //delete a particular recommendation
    @DeleteMapping()
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        recommendation rec = recRepository.getOne(id);
        if(!recRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recommendation Id does not exist");
        if (!rec.getMyStatus().equals(recommendation.status.DRAFT))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status should be only draft");
        recRepository.deleteById(id);
    }

    @PutMapping()
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRecommendation(@PathVariable Long id, @RequestBody UpdateRecommendationRequestDTO Recommendation){

        // Checks for invalid recommendation id
        if(!recRepository.existsById(id))
            return ResponseEntity.badRequest().body(new messageResponse("Recommendation Id is not Valid"));

        recommendation existingRecommendation = recRepository.getReferenceById(id);
        Long authorId =  existingRecommendation.getUserId();
        Long requestUserId = Recommendation.getUserId();

        // Checks for invalid user id
        if(!UserProfileRepository.existsById(requestUserId))
            return ResponseEntity.badRequest().body(new messageResponse("User Id is not Valid"));

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
                && ((Objects.equals(Recommendation.getMyStatus(),recommendation.status.PENDING))
                || (Objects.equals(Recommendation.getMyStatus(),recommendation.status.DRAFT)))){
            existingRecommendation.setSubject(Recommendation.getSubject());
            existingRecommendation.setDescription(Recommendation.getDescription());
            existingRecommendation.setIsPrivate(Recommendation.getIsPrivate());
            existingRecommendation.setMyStatus(Recommendation.getMyStatus());
            existingRecommendation.setModifiedAt(lt);
            return ResponseEntity.ok().body(recRepository.saveAndFlush(existingRecommendation));
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
            return ResponseEntity.ok().body(recRepository.saveAndFlush(existingRecommendation));
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
            return ResponseEntity.ok().body(recRepository.saveAndFlush(existingRecommendation));
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
            return ResponseEntity.ok().body(recRepository.saveAndFlush(existingRecommendation));
        }
        // User is not authorised to update draft
//       throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operation not valid");
        return ResponseEntity.badRequest().body(new messageResponse("Operation not valid or not allowed to perform"));
    }

    // Search API using UserName and Subject
    @GetMapping
    @RequestMapping(value = "/search/{category}", method = RequestMethod.GET)
    public List<RecommendationResponse> searchFilterList(@PathVariable String category, @RequestBody final RecommendationResponse searchParams) {
        Long userId=searchParams.getUserId();
        String categoryTab=category.toUpperCase();

        // If userId is not valid
        if(!UserProfileRepository.existsById(userId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Id does not exist");
        userProfile userdata = UserProfileRepository.getReferenceById(userId);

        // USER Role not allowed to search in Archived records
        if (Objects.equals(userdata.getRoles(),userProfile.roles_enum.USER) && Objects.equals(categoryTab,ARCHIVED) )
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operation not valid");

        // Validations: default values to Query body
        if(Objects.equals(searchParams.getSubject(), null))searchParams.setSubject("");
        if(Objects.equals(searchParams.getUserName(), null))searchParams.setUserName("");

        // Search according to the category type
        switch (categoryTab) {
            case ARCHIVED:
                return recRepository.findArchivedRecommendationsSearch(searchParams.getSubject().toLowerCase(),searchParams.getUserName().toLowerCase());
            case DRAFT:
                return recRepository.findDraftRecommendationsSearch(searchParams.getSubject().toLowerCase(), searchParams.getUserId());
            case MYRECOMMENDATIONS:
                return recRepository.findMyRecommendationsSearch(searchParams.getSubject().toLowerCase(), searchParams.getUserId());
            case ALLRECOMMENDATIONS:
                if (userdata.getRoles().equals(userProfile.roles_enum.USER))
                    return recRepository.findAllUserRecommendationsSearch(searchParams.getSubject().toLowerCase(),searchParams.getUserName().toLowerCase());
                else
                    return recRepository.findAllHrRecommendationsSearch(searchParams.getSubject().toLowerCase(),searchParams.getUserName().toLowerCase());
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operation not valid");
        }
    }
}
