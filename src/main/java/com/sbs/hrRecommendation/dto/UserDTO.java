package com.sbs.hrRecommendation.dto;

import com.sbs.hrRecommendation.models.userProfile;

public class UserDTO {
    private String userName;
    private long employeeId;
    private userProfile.roles_enum roles;
    private userProfile.department departments;

    private String emailId;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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



    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }


}
