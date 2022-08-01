package com.sbs.hrRecommendation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbs.hrRecommendation.dto.ResetPasswordDTO;
import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.payload.response.messageResponse;
import com.sbs.hrRecommendation.repositories.userProfileRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class userProfileController {
    @Autowired
    private PasswordEncoder passwordEncoder;
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
        if(!UserProfileRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Id does not exist");
        return UserProfileRepository.getReferenceById(id);
    }

    @PutMapping
    @RequestMapping(value = "/password/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> resetPassword(@PathVariable Long id, @RequestBody ResetPasswordDTO resetPasswordDTO){
        if (!UserProfileRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
        } else {
            userProfile user = UserProfileRepository.getReferenceById(id);
            if (passwordEncoder.matches(resetPasswordDTO.getOldPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
                UserProfileRepository.saveAndFlush(user);
            } else {
              return ResponseEntity.status(400).body(new messageResponse("Incorrect Password"));
            }
        }
        return ResponseEntity.status(200).body(new messageResponse("Password Updated Successfully"));
    }
    //push data into DB
    /*@PostMapping
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
        userProfile existingUserProfile = UserProfileRepository.getReferenceById(id);
        if(!UserProfileRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Id does not exist");
        BeanUtils.copyProperties(UserProfile, existingUserProfile, "userId");
        return UserProfileRepository.saveAndFlush(existingUserProfile);
    }*/
}
