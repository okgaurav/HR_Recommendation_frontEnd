package com.sbs.hrRecommendation.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.sbs.hrRecommendation.payload.request.signupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.payload.request.loginRequest;
import com.sbs.hrRecommendation.payload.response.jwtResponse;
import com.sbs.hrRecommendation.payload.response.messageResponse;
import com.sbs.hrRecommendation.repositories.userProfileRepository;
import com.sbs.hrRecommendation.security.jwt.jwtUtils;
import com.sbs.hrRecommendation.security.services.userDetailsImpl;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class authController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    userProfileRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    jwtUtils jwtUtils;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody loginRequest loginRequest, HttpServletResponse response) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        userDetailsImpl userDetails = (userDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new jwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getRoles()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody signupRequest signUpRequest) {
        if (signUpRequest.getPassword().length()<8) {
            return ResponseEntity
                    .badRequest()
                    .body(new messageResponse("Error: Password Should be Atleast 8 Characters Long!"));
        }

        if (userRepository.existsByEmailId(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new messageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        userProfile user = new userProfile(
                signUpRequest.getEmployeeId(),
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getRoles(),
                signUpRequest.getDepartments()
               );

        userRepository.save(user);

        return ResponseEntity.ok(new messageResponse("User registered successfully!"));
    }

}

