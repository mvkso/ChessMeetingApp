package com.example.chessmeetingapp.api.response;

import com.example.chessmeetingapp.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponse(
        @JsonProperty("userId") int userId,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("userType") String userType
) {
    public static UserResponse fromUser(User user){
        return new UserResponse(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                user.getUserType().value());
    }
}
