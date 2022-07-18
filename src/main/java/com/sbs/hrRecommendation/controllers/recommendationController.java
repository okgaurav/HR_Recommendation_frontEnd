package com.sbs.hrRecommendation.controllers;

import com.sbs.hrRecommendation.models.recommendation;
import com.sbs.hrRecommendation.repositories.recommendationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/PostRecommendations")
public class recommendationController {

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
