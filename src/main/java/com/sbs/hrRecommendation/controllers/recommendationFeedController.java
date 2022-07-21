package com.sbs.hrRecommendation.controllers;

import com.sbs.hrRecommendation.models.recommendation;
import com.sbs.hrRecommendation.repositories.recommendationRepository;
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
        recommendation.status existingRecommendationStatus = existingRecommendation.getMyStatus();
        LocalDateTime lt = LocalDateTime.now();

        // DRAFT : Author should be same as request owner and Recommendation should be in DRAFT state
        if(Objects.equals(authorId, requestUserId) && Objects.equals(existingRecommendationStatus, recommendation.status.DRAFT)){
            existingRecommendation.setSubject(Recommendation.getSubject());
            existingRecommendation.setDescription(Recommendation.getDescription());
            existingRecommendation.setIsPrivate(Recommendation.getIsPrivate());
            existingRecommendation.setModifiedAt(lt);
            return recRepository.saveAndFlush(existingRecommendation);
        }

        // User is not authorised to update draft
        System.out.println("Unauthorised access or Status is not DRAFT");
        return recRepository.saveAndFlush(existingRecommendation);
    }
}
