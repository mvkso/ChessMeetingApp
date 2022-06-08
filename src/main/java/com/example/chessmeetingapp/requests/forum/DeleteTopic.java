package com.example.chessmeetingapp.requests.forum;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeleteTopic(
        @JsonProperty("topicId") int topicId
) {
}
