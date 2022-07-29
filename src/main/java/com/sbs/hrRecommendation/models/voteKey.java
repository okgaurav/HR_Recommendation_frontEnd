package com.sbs.hrRecommendation.models;

import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class voteKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    public voteKey() {

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

    @Column(name = "recommendation_id")
    private Long recommendationId;


    public voteKey(Long userId, Long recommendationId) {
        this.userId = userId;
        this.recommendationId = recommendationId;
    }
}
