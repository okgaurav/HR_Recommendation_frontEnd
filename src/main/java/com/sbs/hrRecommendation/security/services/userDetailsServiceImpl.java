package com.sbs.hrRecommendation.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.repositories.userProfileRepository;
@Service
public class userDetailsServiceImpl implements UserDetailsService {
    @Autowired
    userProfileRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        userProfile user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with Email: " + email));
        return userDetailsImpl.build(user);
    }
}
