package com.example.chessmeetingapp.requests.forum;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddAnswer(
        @JsonProperty("topicId") int topicId,
        @JsonProperty("userId") int userId,
        @JsonProperty("content") String content
) {
}
