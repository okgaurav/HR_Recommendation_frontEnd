package com.sbs.hrRecommendation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity(name = "recommendationvotes")
public class votes {

    @EmbeddedId
    private voteKey votekey;

    public boolean isUpvote() {
        return isUpvote;
    }

    public void setUpvote(boolean upvote) {
        isUpvote = upvote;
    }

    @JsonProperty
    @Column(name = "is_upvote")
    private boolean isUpvote;


    public voteKey getVotekey() {
        return votekey;
    }

    public void setVotekey(voteKey votekey) {
        this.votekey = votekey;
    }

}
