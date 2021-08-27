package com.example.chessmeetingapp.requests.usersData;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record EditPhoneNumberRequest(
        @NotBlank
        @JsonProperty("newPhoneNumber")
        String newPhoneNumber
) {
}
