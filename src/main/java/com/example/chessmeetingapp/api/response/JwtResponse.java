package com.example.chessmeetingapp.api.response;

import com.example.chessmeetingapp.entities.UserType;

public class JwtResponse {
    private String token;
    private String tokenType = "Bearer";
    private int id;
    private String email;
    private UserType userType;

    public JwtResponse(String token, int id, String email, UserType userType) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.userType = userType;
    }


    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
