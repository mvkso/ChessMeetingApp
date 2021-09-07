package com.example.chessmeetingapp.requests.signInUp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupRequest {


    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    @NotBlank
    @Size(min=5, max=40)
    private String password;

    @NotBlank
    @Size(min=5, max=40)
    private String confirmedPassword;

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotBlank
    private String phoneNumber;

    private String userType;
    

    public SignupRequest(){
    }

    public SignupRequest(String email, String password, String confirmedPassword, String name, String lastName, String phoneNumber, String userType, String region) {
        this.email = email;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
