package com.sbs.hrRecommendation.controllers;

import com.sbs.hrRecommendation.models.recommendation;
import com.sbs.hrRecommendation.repositories.recommendationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class recommendationController {

    @Autowired
    private recommendationRepository recRepository;

    @GetMapping
    public List<recommendation> list() {
        return recRepository.findAll();
    }

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

    //push data into DB
    @PostMapping
    public recommendation create(@RequestBody final recommendation Recommendation) {
        Recommendation.setMyStatus(recommendation.status.SENT);
        return recRepository.saveAndFlush(Recommendation);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        recRepository.deleteById(id);
    }

    //Used to update the record.
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public recommendation update(@PathVariable Long id, @RequestBody recommendation Recommendation) {
        recommendation existingUserProfile = recRepository.getOne(id);
        BeanUtils.copyProperties(Recommendation, existingUserProfile, "userId");
        return recRepository.saveAndFlush(existingUserProfile);
    }
}
