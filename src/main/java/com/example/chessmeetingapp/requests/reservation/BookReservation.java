package com.example.chessmeetingapp.requests.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record BookReservation(
        @JsonProperty("reservationId") int reservationId,
        @JsonProperty("userId") int userId
) {
}
