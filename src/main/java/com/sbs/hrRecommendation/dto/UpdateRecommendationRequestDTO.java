package com.sbs.hrRecommendation.dto;

import com.sbs.hrRecommendation.models.recommendation;

public class UpdateRecommendationRequestDTO {
    private Long userId;
    private String subject;
    private String description;
    private boolean isPrivate;
    private recommendation.status myStatus;
    private boolean isArchived;
    private String feedback;

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

    public recommendation.status getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(recommendation.status myStatus) {
        this.myStatus = myStatus;
    }

    public boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public UpdateRecommendationRequestDTO(Long userId, String subject, String description, boolean isPrivate, recommendation.status myStatus, boolean isArchived, String feedback) {
        this.userId = userId;
        this.subject = subject;
        this.description = description;
        this.isPrivate = isPrivate;
        this.myStatus = myStatus;
        this.isArchived = isArchived;
        this.feedback = feedback;
    }
}
