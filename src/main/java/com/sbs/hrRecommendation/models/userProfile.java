package com.sbs.hrRecommendation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "users")
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "user_name"),
                @UniqueConstraint(columnNames = "email_id")
        })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class userProfile {

    public userProfile(Long employeeId, String userName, String emailId,  String password,roles_enum roles, department departments) {
        this.employeeId = employeeId;
        this.userName = userName;
        this.emailId = emailId;
        this.password = password;
        this.roles = roles;
        this.departments = departments;
    }

    userProfile(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name="employee_id")
    private Long employeeId;
    @Column(name="user_name")
    @NotBlank
    @Size(max = 20)
    private String userName;
    @Column(name="email_id")
    @NotBlank
    @Size(max = 50)
    @Email
    private String emailId;
    @Column(name="profile_photo")
    private String profilePhoto;
    @Column(name="is_active")
    private Boolean isActive=true;
    @Column(name="created_at")
    private LocalDateTime createdAt=LocalDateTime.now();
    @Column(name="modified_at")
    private LocalDateTime modifiedAt=LocalDateTime.now();

    @Column(name="password")
    @NotBlank
    private String password;

    @OneToMany(mappedBy = "users")
    private List<recommendation> recommendations;

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

//    @Column(name="password")
//    private String password;
    @Column(name="phone_num")
    private String phoneNum;
    @Column(name="designation")
    private String designation;

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }


    public roles_enum getRoles() {
        return roles;
    }

    public void setRoles(roles_enum roles) {
        this.roles = roles;
    }

    @Column(name="roles")
//    @NotNull
    @Enumerated(EnumType.STRING)
    public roles_enum roles;

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

    public enum department{
        RD,
        FINANCE,
        SALES_MARKETING,
        MEDIA_MANAGEMENT,
        HR
    }

}
