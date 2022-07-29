package com.sbs.hrRecommendation.controllers;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbs.hrRecommendation.dto.ResetPasswordDTO;
import com.sbs.hrRecommendation.payload.request.ResetPasswordRequest;
import com.sbs.hrRecommendation.payload.request.signupRequest;
import com.sbs.hrRecommendation.security.services.userDetailsServiceImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

        // create a cookie
        Cookie cookie = new Cookie("auth-token", jwt);
        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

//add cookie to response
        response.addCookie(cookie);

        return ResponseEntity.ok(new jwtResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getRoles()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody signupRequest signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new messageResponse("Error: Username is already taken!"));
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


    @Autowired
    private ResetPasswordRequest resetPasswordRequest;


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestHeader(value = "Authorization") String token, @RequestBody ResetPasswordDTO resetPasswordDTO){
        String response = "";
        Object body = new Object();
        try{
            body = jwtUtils.getUserIdFromJwtToken(token.substring(7, token.length()));
        }catch (SignatureException e) {
            response = "Invalid JWT signature" ;
        } catch (MalformedJwtException e) {
            response = "Invalid JWT token";
        } catch (ExpiredJwtException e) {
            response = "JWT token is expired";
        } catch (UnsupportedJwtException e) {
            response = "JWT token is unsupported";
        } catch (IllegalArgumentException e) {
            response = "JWT claims string is empty";
        }
        if(!response.equals("")){
            return ResponseEntity.badRequest().body(response);
        }
        System.out.println(body);
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(body, Map.class);
        long hello = (Integer) map.get("id");
        System.out.println(hello);
        response = resetPasswordRequest.resetPassword((long) 28, resetPasswordDTO);
        return ResponseEntity.ok(response);
    }
}

