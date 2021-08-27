package com.example.chessmeetingapp.requests.usersData;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record ChangeUserEmailRequest(
        @NotBlank
        @Size(max = 100)
        @Email
        @JsonProperty("email")
        String email
)
{
}
