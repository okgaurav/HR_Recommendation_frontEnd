package com.sbs.hrRecommendation.models;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Time;

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
    private boolean isPrivate;
    @Column(name="created_at")
    private Time createdAt;
    @Column(name="modified_at")
    private Time modifiedAt;
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private status Status;

    public status getStatus() {
        return Status;
    }

    public void setDepartments(status Status) {
        this.Status = Status;
    }

    private enum status{
        SENT,
        IN_REVIEW,
        APPROVED,
        DECLINED
    }
//    @Lob
//    @Type(type = "org.hibernate.type.BinaryType")
//    @Column(name="media_files")
//    private Byte[] mediaFiles;

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

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Time getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Time createdAt) {
        this.createdAt = createdAt;
    }

    public Time getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Time modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
}

