package com.sbs.hrRecommendation.payload.request;

import com.sbs.hrRecommendation.models.userProfile;

import java.util.Set;

import javax.validation.constraints.*;

public class signupRequest {

    private String userName;

    private long employeeId;

    private userProfile.roles_enum roles;

    private userProfile.department departments;


    private String emailId;


    private String password;

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public userProfile.roles_enum getRoles() {
        return roles;
    }

    public void setRoles(userProfile.roles_enum roles) {
        this.roles = roles;
    }

    public userProfile.department getDepartments() {
        return departments;
    }

    public void setDepartments(userProfile.department departments) {
        this.departments = departments;
    }

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
