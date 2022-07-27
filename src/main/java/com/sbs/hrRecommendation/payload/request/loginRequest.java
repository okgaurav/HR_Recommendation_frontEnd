package com.sbs.hrRecommendation.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class loginRequest {
    @NotBlank
    @Email
    private String emailId;

    @NotBlank
    private String password;

    public String getEmail() {
        return emailId;
    }

    public void setEmail(String email) {
        this.emailId = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}