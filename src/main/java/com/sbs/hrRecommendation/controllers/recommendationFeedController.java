package com.sbs.hrRecommendation.controllers;

import com.sbs.hrRecommendation.models.recommendation;
import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.repositories.recommendationRepository;
import com.sbs.hrRecommendation.repositories.userProfileRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/recommendations")
public class recommendationFeedController {

    @Autowired
    private recommendationRepository recRepository;
    @Autowired
    private userProfileRepository UserProfileRepository;

    @GetMapping
    public List<recommendation> list() {
        return recRepository.findAll();
    }
    //To get data of a user using a particular id
    @GetMapping
    @RequestMapping("{id}")
    public recommendation get(@PathVariable Long id){
        return recRepository.getOne(id);
    }
    //push data into DB
    @PostMapping
    public recommendation create(@RequestBody final recommendation Recommendation) {
        Recommendation.setMyStatus(recommendation.status.SENT);
        return recRepository.saveAndFlush(Recommendation);
    }

    //Used to update the record.
    @PutMapping("{id}")
    public recommendation updateRecommendation(@PathVariable Long id, @RequestBody recommendation Recommendation) throws Exception {
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
                && Objects.equals(existingRecommendationStatus, recommendation.status.DRAFT)){
            existingRecommendation.setSubject(Recommendation.getSubject());
            existingRecommendation.setDescription(Recommendation.getDescription());
            existingRecommendation.setIsPrivate(Recommendation.getIsPrivate());
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
                && !Objects.equals(Recommendation.getMyStatus(),recommendation.status.DRAFT)) {
            existingRecommendation.setMyStatus(Recommendation.getMyStatus());
            existingRecommendation.setModifiedAt(lt);
        }

       /*
            ARCHIVED:
            Requested owner should be HR,
            Author should not be same as requested owner,
            Recommendation status should be APPROVED, DECLINED, PENDING
       */
        if(Objects.equals(requestUserRole, userProfile.roles_enum.HR)
                && !Objects.equals(authorId,requestUserId)
                && (Objects.equals(existingRecommendationStatus, recommendation.status.APPROVED)
                ||  Objects.equals(existingRecommendationStatus, recommendation.status.DECLINED)
                || Objects.equals(existingRecommendationStatus, recommendation.status.PENDING))) {
            existingRecommendation.setMyStatus(Recommendation.getMyStatus());
            existingRecommendation.setModifiedAt(lt);
        }

        // User is not authorised to update draft
        System.out.println("Unauthorised access or Status is not DRAFT");
        return recRepository.saveAndFlush(existingRecommendation);
    }
}


