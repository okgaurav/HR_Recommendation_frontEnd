package com.sbs.hrRecommendation.repositories;


import com.sbs.hrRecommendation.dto.RecommendationResponse;
import com.sbs.hrRecommendation.models.recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface recommendationRepository extends JpaRepository<recommendation, Long> {
    List<recommendation> findByUserIdAndIsArchivedAndMyStatusNot(Long id, Boolean archive, recommendation.status status);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles, r.count, r.countUpvote, r.countDownvote)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.userId=?1 and r.myStatus <> 'DRAFT' and r.isArchived = false")
    List<RecommendationResponse> findMyRecommendations(Long id);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles, r.count, r.countUpvote, r.countDownvote)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.recommendationId=?1")
    RecommendationResponse findRecommendation(Long id);
    List<recommendation> findByIsArchivedAndMyStatusNot(Boolean archive, recommendation.status status);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles, r.count, r.countUpvote, r.countDownvote)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.myStatus <> 'DRAFT' and r.isArchived = false")
    List<RecommendationResponse> findAllHrRecommendations();
    List<recommendation> findByIsArchivedAndMyStatusNotAndIsPrivate(Boolean archive, recommendation.status status, Boolean isPrivate);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles, r.count, r.countUpvote, r.countDownvote)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.myStatus <> 'DRAFT' and r.isArchived = false and r.isPrivate=false")
    List<RecommendationResponse> findAllUserRecommendations();
    void deleteByRecommendationIdAndMyStatus(Long id, recommendation.status status);

    List<recommendation> findByIsArchived(Boolean archive);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles, r.count, r.countUpvote, r.countDownvote)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.isArchived = true and r.myStatus<>'DRAFT'")
    List<RecommendationResponse> findArchived();
    List<recommendation> findByUserIdAndMyStatus(Long id, recommendation.status status);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles, r.count, r.countUpvote, r.countDownvote)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.userId=?1 and r.myStatus = 'DRAFT'")
    List<RecommendationResponse> findDrafts(Long id);
    //We created this interface because  we get operations like find, update, delete.


    /* FILTER AND SEARCHING */
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles, r.count, r.countUpvote, r.countDownvote)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.myStatus <> 'DRAFT' and r.isArchived = false and r.isPrivate=false"+
            " and LOWER(r.subject) LIKE %?1% and LOWER(u.userName) LIKE %?2%")
    List<RecommendationResponse> findAllUserRecommendationsSearch(String subject, String userName);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles, r.count, r.countUpvote, r.countDownvote)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.myStatus <> 'DRAFT' and r.isArchived = false"+
            " and LOWER(r.subject) LIKE %?1% and LOWER(u.userName) Like %?2%")
    List<RecommendationResponse> findAllHrRecommendationsSearch(String subject, String userName);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles, r.count, r.countUpvote, r.countDownvote)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.myStatus <> 'DRAFT' and r.isArchived = true"+
            " and LOWER(r.subject) LIKE %?1% and LOWER(u.userName) LIKE %?2%")
    List<RecommendationResponse> findArchivedRecommendationsSearch(String subject, String userName);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles, r.count, r.countUpvote, r.countDownvote)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.userId=?2 and r.myStatus = 'DRAFT'"+
            " and LOWER(r.subject) LIKE %?1%")
    List<RecommendationResponse> findDraftRecommendationsSearch(String subject, Long id);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles, r.count, r.countUpvote, r.countDownvote)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.userId=?2 and r.myStatus <> 'DRAFT' and r.isArchived = false"+
            " and LOWER(r.subject) LIKE %?1%")
    List<RecommendationResponse> findMyRecommendationsSearch(String subject, Long id);
}
