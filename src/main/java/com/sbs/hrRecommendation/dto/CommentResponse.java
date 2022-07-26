package com.sbs.hrRecommendation.dto;


import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.models.comments;

import java.time.LocalDateTime;

public class CommentResponse {
    private Long recommendationId;
    private Long userId;
    private Long commentId;
    private String commentDescription;
    private comments.comment_type comment_type;
    private LocalDateTime created_at;
    private String userName;
    private Long employeeId;
    private String designation;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String commentDescription) {
        commentDescription = commentDescription;
    }

    public comments.comment_type getComment_type() {
        return comment_type;
    }

    public void setComment_type(comments.comment_type comment_type) {
        this.comment_type = comment_type;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

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

    public CommentResponse(Long recommendationId, Long userId, Long commentId, String commentDescription, comments.comment_type comment_type, LocalDateTime created_at, String userName, Long employeeId, String designation, userProfile.roles_enum roles) {
        this.recommendationId = recommendationId;
        this.comment_type=comment_type;
        this.commentId=commentId;
        this.commentDescription=commentDescription;
        this.created_at=created_at;
        this.userName = userName;
        this.designation = designation;
        this.userId = userId;
        this.employeeId = employeeId;
        this.roles = roles;
    }
}
