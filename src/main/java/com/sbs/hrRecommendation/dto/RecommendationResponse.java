package com.sbs.hrRecommendation.dto;


import com.sbs.hrRecommendation.models.recommendation;
import com.sbs.hrRecommendation.models.userProfile;

import java.time.LocalDateTime;

public class RecommendationResponse {
    private Long recommendationId;
    private Long userId;
    private String subject;
    private String description;
    private boolean isPrivate;
    private LocalDateTime modifiedAt;
    private recommendation.status myStatus;
    private boolean isArchived;
    private String userName;
    private Long employeeId;
    private String designation;
    private userProfile.roles_enum roles;

    public Long getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(Long recommendationId) {
        this.recommendationId = recommendationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public recommendation.status getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(recommendation.status myStatus) {
        this.myStatus = myStatus;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public userProfile.roles_enum getRoles() {
        return roles;
    }

    public void setRoles(userProfile.roles_enum roles) {
        this.roles = roles;
    }

    public RecommendationResponse(Long recommendationId, Long userId, String subject, String description, boolean isPrivate, LocalDateTime modifiedAt, recommendation.status myStatus, boolean isArchived, String userName, Long employeeId, String designation, userProfile.roles_enum roles) {
        this.recommendationId = recommendationId;
        this.userName = userName;
        this.designation = designation;
        this.userId = userId;
        this.subject = subject;
        this.description = description;
        this.isPrivate = isPrivate;
        this.modifiedAt = modifiedAt;
        this.myStatus = myStatus;
        this.isArchived = isArchived;
        this.employeeId = employeeId;
        this.roles = roles;
    }
}
