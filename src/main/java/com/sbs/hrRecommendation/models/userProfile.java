package com.sbs.hrRecommendation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class userProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;
    @Column(name="employee_id")
    private Long employeeId;
    @Column(name="user_name")
    private String userName;
    @Column(name="email_id")
    private String emailId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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

//    public String getRoles() {
//        return roles;
//    }
//
//    public void setRoles(String roles) {
//        this.roles = roles;
//    }

//    public String getDepartments() {
//        return departments;
//    }
//
//    public void setDepartments(String departments) {
//        this.departments = departments;
//    }

    @Column(name="password")
    private String password;
    @Column(name="phone_num")
    private String phoneNum;
    @Column(name="designation")
    private String designation;

//    public byte[] getProfilePhoto() {
//        return profilePhoto;
//    }
//
//    public void setProfilePhoto(byte[] profilePhoto) {
//        this.profilePhoto = profilePhoto;
//    }

//    @Lob //Lob stands for large object. Binary data can get very large so this annotation helps JPA in dealing with larger data.
//    @Type(type="org.hibernate.type.BinaryType") //This annotation helps hibernate to deal with binary data.
//    @Column(name="profile_photo")
//    private byte[] profilePhoto;

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    @Column(name="profile_photo")
    private String profilePhoto;
    @Column(name="is_active")
    private Boolean isActive;
    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="modified_at")
    private LocalDateTime modifiedAt;

    public roles_enum getRoles() {
        return roles;
    }

    public void setRoles(roles_enum roles) {
        this.roles = roles;
    }

    @Column(name="roles")
//    @NotNull
    @Enumerated(EnumType.STRING)
    private roles_enum roles;

    public enum roles_enum{
        ADMIN,
        HR,
        USER
    }

    @Column(name="departments")
//    @NotNull
    @Enumerated(EnumType.STRING)
    private department departments;

    public department getDepartments() {
        return departments;
    }

    public void setDepartments(department departments) {
        this.departments = departments;
    }

    private enum department{
        RD,
        FINANCE,
        SALESMARKETING,
        MEDIAMANAGEMENT,
        HR
    }
//    @Column(name="departments")
//    private String departments;

//    public userProfile() {
//
//    }
}
