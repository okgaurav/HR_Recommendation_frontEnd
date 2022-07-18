package com.sbs.hrRecommendation.repositories;


import com.sbs.hrRecommendation.models.recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface recommendationRepository extends JpaRepository<recommendation, Long> { //We created this interface because  we get operations like find, update, delete.
}
