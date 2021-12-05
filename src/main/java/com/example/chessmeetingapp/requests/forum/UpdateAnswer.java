package com.example.chessmeetingapp.requests.forum;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateAnswer(
        @JsonProperty("answerId") int answerId,
        @JsonProperty("action") String action
) {
}
