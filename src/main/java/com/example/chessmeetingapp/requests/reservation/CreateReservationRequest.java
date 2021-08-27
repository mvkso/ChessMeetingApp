package com.example.chessmeetingapp.requests.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record CreateReservationRequest(
        @JsonProperty("userId") int userId,
        @JsonProperty("DateTimeFrom") LocalDateTime timeFrom,
        @JsonProperty("DateTimeTo") LocalDateTime timeTo,
        @JsonProperty("Subject") String Subject,
        @JsonProperty("Address") String Address,
        @JsonProperty("Slots") int Slots
) {
}
