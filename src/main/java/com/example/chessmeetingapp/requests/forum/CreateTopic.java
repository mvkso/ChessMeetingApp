package com.example.chessmeetingapp.requests.forum;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateTopic(
        @JsonProperty("title") String title,
        @JsonProperty("content") String content,
        @JsonProperty("category") String category,
        @JsonProperty("userId") int userId
) {
}
