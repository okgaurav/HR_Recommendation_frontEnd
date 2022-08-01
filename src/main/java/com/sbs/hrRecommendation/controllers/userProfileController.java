package com.sbs.hrRecommendation.controllers;

import com.sbs.hrRecommendation.Services.NewUserEmailImp;
import com.sbs.hrRecommendation.dto.ResetPasswordDTO;
import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.payload.response.messageResponse;
import com.sbs.hrRecommendation.repositories.userProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class userProfileController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private userProfileRepository UserProfileRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private NewUserEmailImp newUserEmailImp;


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
    @PostMapping(value = "/create/{id}")
    public ResponseEntity<?> adduser(@PathVariable("id") Long id, @RequestBody userProfile user) {
        String email_sub = "HR Recommendation || Status Update";
        userProfile userdata = UserProfileRepository.getReferenceById(id);
        System.out.println(userdata.getUserId());
        Map<String, Object> map = new HashMap<>();

        map.put("email",user.getEmailId());

        if(Objects.equals(userdata.getRoles(),userProfile.roles_enum.ADMIN))
            {
//                userProfile newUser = new userProfile(
//                        user.getEmployeeId(),
//                        user.getUserName(),
//                        user.getEmailId(),
//                        encoder.encode("12345678"),
//                        user.getRoles(),
//                        user.getDepartments());
                String password= getPassword();
                map.put("pass", password);
                user.setPassword(encoder.encode(password));
                System.out.println(user);
                UserProfileRepository.saveAndFlush(user);

                newUserEmailImp.newUserMail(user.getEmailId(), email_sub, map);

                // UserProfileRepository.saveAndFlush(newUser);
                 return ResponseEntity.status(200).body(new messageResponse("User Added Successfully"));
        }

        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operation not valid");
        }
    }
    static String getPassword()
    {
        int n=8;

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        System.out.println(sb.toString());

        return sb.toString();
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
