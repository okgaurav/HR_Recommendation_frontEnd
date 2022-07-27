package com.sbs.hrRecommendation.payload.response;

import com.sbs.hrRecommendation.models.userProfile;

import java.util.List;

public class jwtResponse {
    private userProfile.roles_enum roles;
    private Long id;
    private String username;

    public jwtResponse( Long id, String username, userProfile.roles_enum roles) {
        this.id = id;
        this.username = username;
        this.roles= roles;
    }

    public userProfile.roles_enum getRoles() {
        return roles;
    }

    public void setRoles(userProfile.roles_enum roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}