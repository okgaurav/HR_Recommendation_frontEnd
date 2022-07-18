package com.sbs.hrRecommendation.controllers;

import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.repositories.userProfileRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/UserProfile")
public class userProfileController {

    @Autowired
    private userProfileRepository UserProfileRepository;

    @GetMapping
    public List<userProfile> list() {
        return UserProfileRepository.findAll();
    }

    //To get data of a user using a particular id
    @GetMapping
    @RequestMapping("{id}")
    public userProfile get(@PathVariable Long id){
        return UserProfileRepository.getOne(id);
    }

    //push data into DB
    @PostMapping
    public userProfile create(@RequestBody final userProfile UserProfile) {
        return UserProfileRepository.saveAndFlush(UserProfile);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        UserProfileRepository.deleteById(id);
    }

    //Used to update the record.
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public userProfile update(@PathVariable Long id, @RequestBody userProfile UserProfile) {
        userProfile existingUserProfile = UserProfileRepository.getOne(id);
        BeanUtils.copyProperties(UserProfile, existingUserProfile, "userId");
        return UserProfileRepository.saveAndFlush(existingUserProfile);
    }
}
