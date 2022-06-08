package com.example.chessmeetingapp.api.response;

import com.example.chessmeetingapp.entities.Log;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record LogResponse(
        @JsonProperty("logId") int logId,
        @JsonProperty("date") LocalDateTime date,
        @JsonProperty("logType") String logType,
        @JsonProperty("description") String description
) {

    public static LogResponse fromLog(Log log){
        return new LogResponse(
                log.getLogId(),
                log.getDate(),
                log.getLogType().getValue(),
                log.getDescription()
        );
    }
}