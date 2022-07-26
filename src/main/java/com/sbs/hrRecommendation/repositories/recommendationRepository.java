package com.sbs.hrRecommendation.repositories;


import com.sbs.hrRecommendation.dto.RecommendationResponse;
import com.sbs.hrRecommendation.models.recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface recommendationRepository extends JpaRepository<recommendation, Long> {
    List<recommendation> findByUserIdAndIsArchivedAndMyStatusNot(Long id, Boolean archive, recommendation.status status);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.userId=?1 and r.myStatus <> 'DRAFT' and r.isArchived = false")
    List<RecommendationResponse> findMyRecommendations(Long id);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.recommendationId=?1")
    RecommendationResponse findRecommendation(Long id);
    List<recommendation> findByIsArchivedAndMyStatusNot(Boolean archive, recommendation.status status);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.myStatus <> 'DRAFT' and r.isArchived = false")
    List<RecommendationResponse> findAllHrRecommendations();
    List<recommendation> findByIsArchivedAndMyStatusNotAndIsPrivate(Boolean archive, recommendation.status status, Boolean isPrivate);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.myStatus <> 'DRAFT' and r.isArchived = false and r.isPrivate=false")
    List<RecommendationResponse> findAllUserRecommendations();
    void deleteByRecommendationIdAndMyStatus(Long id, recommendation.status status);

    List<recommendation> findByIsArchived(Boolean archive);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.isArchived = true and r.myStatus<>'DRAFT'")
    List<RecommendationResponse> findArchived();
    List<recommendation> findByUserIdAndMyStatus(Long id, recommendation.status status);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.userId=?1 and r.myStatus = 'DRAFT'")
    List<RecommendationResponse> findDrafts(Long id);
    //We created this interface because  we get operations like find, update, delete.


    /* FILTER AND SEARCHING */
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.myStatus <> 'DRAFT' and r.isArchived = false and r.isPrivate=false"+
            " and r.subject LIKE '%subject%' and u.userName Like '%name%' and r.myStatus=status")
    List<RecommendationResponse> findAllUserSearch(@Param("subject") String subject,@Param("name")String userName,@Param("status") recommendation.status myStatus);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.myStatus <> 'DRAFT' and r.isArchived = false"+
            " and r.subject LIKE '%subject%' and u.userName Like '%name%' and r.myStatus=status and r.isPrivate=private")
    List<RecommendationResponse> findAllHrSearch(@Param("subject") String subject,@Param("name")String userName,@Param("status") recommendation.status myStatus,@Param("private") Boolean isPrivate);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.myStatus <> 'DRAFT' and r.isArchived = true"+
            " and r.subject LIKE '%subject%' and u.userName Like '%name%' and r.myStatus=status and r.isPrivate=private")
    List<RecommendationResponse> findArchivedSearch(@Param("subject") String subject,@Param("name")String userName,@Param("status") recommendation.status myStatus,@Param("private") Boolean isPrivate);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.userId = :id and r.myStatus = 'DRAFT'"+
            " and r.subject LIKE '%:subject%' and r.isPrivate = :private")
    List<RecommendationResponse> findDraftSearch(@Param("subject") String subject,@Param("private") Boolean isPrivate,@Param("id")Long id);
    @Query("SELECT new com.sbs.hrRecommendation.dto.RecommendationResponse(r.recommendationId, r.userId, r.subject, r.description," +
            " r.isPrivate, r.modifiedAt, r.myStatus, r.isArchived, u.userName, u.employeeId, u.designation, u.roles)" +
            " FROM recommendations r, users u where r.userId = u.userId and r.userId = :id and r.myStatus <> 'DRAFT' and r.isArchived = false"+
            " and r.subject LIKE '%:subject%' and r.myStatus=:status and r.isPrivate=:private ")
    List<RecommendationResponse> findMySearch(@Param("subject") String subject,@Param("status") recommendation.status myStatus,@Param("private") Boolean isPrivate,@Param("id")Long id);
}
