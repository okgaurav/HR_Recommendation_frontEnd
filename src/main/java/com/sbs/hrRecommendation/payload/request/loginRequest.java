package com.sbs.hrRecommendation.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;

public class loginRequest {
    @NotBlank
    @Email
    private String email_id;

    @NotBlank
    private String password;

    public String getEmail() {
        return email_id;
    }

    public void setEmail(String email_id) {
        this.email_id = email_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}