package com.sbs.hrRecommendation.payload.request;

import com.sbs.hrRecommendation.dto.ResetPasswordDTO;
import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.payload.response.messageResponse;
import com.sbs.hrRecommendation.repositories.userProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import com.sbs.hrRecommendation.security.webSecurityConfig.*;
import com.sbs.hrRecommendation.payload.response.messageResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ResetPasswordRequest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private userProfileRepository userProfileRepository;
    public String resetPassword(long Id, ResetPasswordDTO resetPasswordDTO) {
        String encodedOldPassword = passwordEncoder.encode(resetPasswordDTO.getOldPassword());
        Optional<userProfile> existingId = userProfileRepository.findById(Id);
        if (!existingId.isPresent()) {
            return "Error: User doesnot exist!";
        } else {
            userProfile user = existingId.get();
            if (passwordEncoder.matches(resetPasswordDTO.getOldPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
                userProfileRepository.saveAndFlush(user);
            } else {
                return "Incorrect Old Password";
            }
        }
        return "Password reset Successfully";
    }
}
