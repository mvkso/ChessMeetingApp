package com.example.chessmeetingapp.requests.usersData;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank
        @Size(min=5, max=40)
        @JsonProperty("oldPassword")
        String oldPassword,
        @NotBlank
        @Size(min=5, max=40)
        @JsonProperty("newPassword")
        String newPassword,
        @NotBlank
        @Size(min=5, max=40)
        @JsonProperty("confirmedNewPassword")
        String confirmedNewPassword
) {
}
