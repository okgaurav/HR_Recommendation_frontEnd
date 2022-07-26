package com.sbs.hrRecommendation.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="comments")
public class comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "recommendation_id")
    private Long recommendationId;
    @Column(name = "comment_desc")
    private String commentDescription;
    @Enumerated(EnumType.STRING)
    @Column(name = "comment_type")
    private comments.comment_type commentType;

    public enum comment_type{

        DISPLAY,
        FEEDBACK
    }

    public comment_type getCommentType() {
        return commentType;
    }

    public void setCommentType(comment_type commentType) {
        this.commentType = commentType;
    }

    @Column(name="created_at")
    private LocalDateTime createdAt=LocalDateTime.now();
    @Column(name="modified_at")
    private LocalDateTime modifiedAt=LocalDateTime.now();

    @ManyToOne
    private recommendation Recommendation;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(Long recommendationId) {
        this.recommendationId = recommendationId;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}