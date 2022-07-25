package com.sbs.hrRecommendation.controllers;

import com.sbs.hrRecommendation.customException.NotFoundException;
import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.repositories.userProfileRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/Users_New")
public class userProfileController {

    @Autowired
    private userProfileRepository UserProfileRepository;

    @GetMapping
    public List<userProfile> list() {
        return UserProfileRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public userProfile get(@PathVariable Long id){
        if(!UserProfileRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Id does not exist");
        return UserProfileRepository.getReferenceById(id);
    }

    @PostMapping
    public userProfile create(@RequestBody final userProfile UserProfile) {
        return UserProfileRepository.saveAndFlush(UserProfile);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        if(!UserProfileRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Id does not exist");
        UserProfileRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT) //Used to update the record.
    public userProfile update(@PathVariable Long id, @RequestBody userProfile UserProfile) {
        if(!UserProfileRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Id does not exist");
        }
        userProfile existingUserProfile = UserProfileRepository.getOne(id);
        BeanUtils.copyProperties(UserProfile, existingUserProfile, "userId");
        return UserProfileRepository.saveAndFlush(existingUserProfile);
    }
}
