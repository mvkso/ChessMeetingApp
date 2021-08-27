package com.example.chessmeetingapp.requests.usersData;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EditNameOrLastNameRequest(
        @JsonProperty("name")
        String name,

        @JsonProperty("lastName")
        String lastName
) {
}
