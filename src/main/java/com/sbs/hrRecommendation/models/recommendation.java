package com.sbs.hrRecommendation.models;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name="recommendations")
public class recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recommendation_id")
    private Long recommendationId;
    @Column(name="user_id")
    private Long userId;
    @Column(name="subject")
    private String subject;
    @Column(name="description")
    private String description;
    @Column(name="is_private")
    public boolean isPrivate;
    @Column(name="created_at")
    private LocalDateTime createdAt=LocalDateTime.now();
    @Column(name="modified_at")
    private LocalDateTime modifiedAt=LocalDateTime.now();
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private status myStatus;
    @Column(name="is_archived")
    private boolean isArchived;

    @Column(name = "count")
    private Integer count = 0;

    @Column(name = "count_upvote")
    private Integer countUpvote = 0;

    public Integer getCountUpvote() {
        return countUpvote;
    }

    public void setCountUpvote(Integer countUpvote) {
        this.countUpvote = countUpvote;
    }

    public Integer getCountDownvote() {
        return countDownvote;
    }

    public void setCountDownvote(Integer countDownvote) {
        this.countDownvote = countDownvote;
    }

    @Column(name = "count_downvote")
    private Integer countDownvote = 0;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }



    public boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(boolean archived) {
        isArchived = archived;
    }

    public status getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(status myStatus) {
        this.myStatus = myStatus;
    }

    public enum status{

        DRAFT,
        PENDING,
        CHANGES_REQUESTED,
        APPROVED,
        DECLINED
    }
    @ManyToOne
    private userProfile users;

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

    public void setIsPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
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

