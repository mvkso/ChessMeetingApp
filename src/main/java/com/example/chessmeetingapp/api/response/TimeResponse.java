package com.example.chessmeetingapp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record TimeResponse(
        @JsonProperty("time") LocalDateTime time
) {
    public static TimeResponse fromtime(LocalDateTime time){
        return new TimeResponse(time);
    }
}