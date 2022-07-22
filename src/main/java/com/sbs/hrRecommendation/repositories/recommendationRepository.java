package com.sbs.hrRecommendation.repositories;


import com.sbs.hrRecommendation.models.recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface recommendationRepository extends JpaRepository<recommendation, Long> {
    List<recommendation> findByUserIdAndIsArchivedAndMyStatusNot(Long id, Boolean archive, recommendation.status status);
    List<recommendation> findByIsArchivedAndMyStatusNot(Boolean archive, recommendation.status status);
    List<recommendation> findByIsArchivedAndMyStatusNotAndIsPrivate(Boolean archive, recommendation.status status, Boolean isPrivate);
    void deleteByRecommendationIdAndMyStatus(Long id, recommendation.status status);

    List<recommendation> findByIsArchived(Boolean archive);
    List<recommendation> findByUserIdAndMyStatus(Long id, recommendation.status status);
    //We created this interface because  we get operations like find, update, delete.
}
