package com.sbs.hrRecommendation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbs.hrRecommendation.dto.RecommendationResponse;
import com.sbs.hrRecommendation.dto.ResetPasswordDTO;
import com.sbs.hrRecommendation.dto.UserDTO;
import com.sbs.hrRecommendation.models.recommendation;
import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.payload.response.messageResponse;
import com.sbs.hrRecommendation.repositories.userProfileRepository;
import com.sbs.hrRecommendation.security.jwt.authEntryPointJwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class userProfileController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private userProfileRepository UserProfileRepository;

    @Autowired
    PasswordEncoder encoder;


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
              return ResponseEntity.status(400).body(new messageResponse("Invalid Password"));
            }
        }
        return ResponseEntity.status(200).body(new messageResponse("Password Updated Successfully"));
    }
    //push data into DB
    private static final Logger logger = LoggerFactory.getLogger(authEntryPointJwt.class);
    @PostMapping(value = "/create/{id}")
    public ResponseEntity<?> adduser(@PathVariable Long id, @Valid @RequestBody userProfile user) {
        logger.error("first statment");
        userProfile userdata = UserProfileRepository.getReferenceById(id);
        System.out.println("second statement");
        if(Objects.equals(userdata.getRoles(),userProfile.roles_enum.ADMIN))
            {

                System.out.println("third statement");

//                userProfile newUser = new userProfile(
//                        user.getEmployeeId(),
//                        user.getUserName(),
//                        user.getEmailId(),
//                        encoder.encode("12345678"),
//                        user.getRoles(),
//                        user.getDepartments());


                 UserProfileRepository.saveAndFlush(user);
                 return ResponseEntity.status(200).body(new messageResponse("User Added Successfully"));
        }

        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operation not valid");
        }
    }

   /* @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        UserProfileRepository.deleteById(id);
    }*/

    //Used to update the record.
    /*@RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public userProfile update(@PathVariable Long id, @RequestBody userProfile UserProfile) {
        userProfile existingUserProfile = UserProfileRepository.getReferenceById(id);
        if(!UserProfileRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Id does not exist");
        BeanUtils.copyProperties(UserProfile, existingUserProfile, "userId");
        return UserProfileRepository.saveAndFlush(existingUserProfile);
    }*/
}
