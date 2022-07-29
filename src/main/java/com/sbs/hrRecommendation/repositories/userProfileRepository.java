package com.sbs.hrRecommendation.repositories;

import com.sbs.hrRecommendation.dto.ResetPasswordDTO;
import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.payload.response.messageResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public interface userProfileRepository extends JpaRepository<userProfile, Long> { //We created this interface because  we get operations like find, update, delete.
    Optional<userProfile> findByUserName(String username);
    Optional<userProfile> findByEmailId(String email);

    Boolean existsByUserName(String username);
    Boolean existsByEmailId(String email);
}
