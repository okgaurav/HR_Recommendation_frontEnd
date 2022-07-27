package com.sbs.hrRecommendation.repositories;

import com.sbs.hrRecommendation.models.userProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface userProfileRepository extends JpaRepository<userProfile, Long> { //We created this interface because  we get operations like find, update, delete.
    Optional<userProfile> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
