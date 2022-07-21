package com.sbs.hrRecommendation.controllers;
import com.sbs.hrRecommendation.models.recommendation;
import com.sbs.hrRecommendation.repositories.recommendationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class recommendationController {

    @Autowired
    private recommendationRepository recRepository;

    //used to get all the recommendations present in db
    @GetMapping
    public List<recommendation> list() {
        return recRepository.findAll();
    }

    // used to get list of recommendations of a particular user
    @GetMapping
    @RequestMapping("{id}")
    public List<recommendation> listOfRecommendations(@PathVariable Long id) {
        List<recommendation> rec;
        List<recommendation> result  = new ArrayList<recommendation>();
        rec = recRepository.findAll();
        for (int i = 0; i < rec.size(); i++) {

            if(rec.get(i).getUserId() == id) {
                result.add(rec.get(i));
            }
        }
        return result;
    }

    //push recommendation into DB
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
        recommendation rec;
        String result;
        rec = recRepository.getOne(id);
        if (rec.getMyStatus().equals(recommendation.status.DRAFT)){
            recRepository.deleteById(id);
        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status should be only draft");


    }

    //Used to update the record.
//    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
//    public recommendation update(@PathVariable Long id, @RequestBody recommendation Recommendation) {
//        recommendation existingUserProfile = recRepository.getOne(id);
//        BeanUtils.copyProperties(Recommendation, existingUserProfile, "userId");
//        return recRepository.saveAndFlush(existingUserProfile);
//    }
}
