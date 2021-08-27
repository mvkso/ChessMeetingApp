package com.example.chessmeetingapp.requests.usersData;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record EditUserDetailsRequest(
        @NotBlank
        @Size(max = 100)
        @Email
        @JsonProperty("oldEmail")
        String oldEmail,

        @NotBlank
        @Size(max = 100)
        @Email
        @JsonProperty("newEmail")
        String newEmail,

        @NotBlank
        @JsonProperty ("firstName")
        String firstName,

        @NotBlank
        @JsonProperty("lastName")
        String lastName,

        @NotBlank
        @JsonProperty("oldPhoneNumber")
        String oldPhoneNumber,

        @NotBlank
        @JsonProperty("newPhoneNumber")
        String newPhoneNumber,

        @NotBlank
        @JsonProperty("region")
        String region
) {
}
