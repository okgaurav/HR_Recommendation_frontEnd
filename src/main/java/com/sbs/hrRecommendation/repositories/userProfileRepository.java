package com.sbs.hrRecommendation.repositories;

import com.sbs.hrRecommendation.models.userProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userProfileRepository extends JpaRepository<userProfile, Long> { //We created this interface because  we get operations like find, update, delete.
}
