package com.example.chessmeetingapp.api.response;

import com.example.chessmeetingapp.entities.UserDetails;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDetailsResponse(
        @JsonProperty("userDetailsId") int userDetailsId,
         @JsonProperty("firstName") String firstName,
         @JsonProperty("lastName") String lastName,
         @JsonProperty("phoneNumber") String phoneNumber,
         @JsonProperty("user") UserResponse user) {

    public static UserDetailsResponse fromUserDetails(UserDetails userDetails){
        return new UserDetailsResponse(
                userDetails.getId(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getPhoneNumber(),
                UserResponse.fromUser(userDetails.getUser())
        );

    }
}
