package com.example.chessmeetingapp.api.response;

import com.example.chessmeetingapp.entities.Reservation;
import com.example.chessmeetingapp.entities.UserDetails;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Set;

public record ReservationResponse(
        @JsonProperty("Id") int Id,
        @JsonProperty("userCreator") int userCreatorId,
        @JsonProperty("StartTime") LocalDateTime dateTimeFrom,
        @JsonProperty("EndTime") LocalDateTime dateTimeTo,
        @JsonProperty("Subject") String subject,
        @JsonProperty("cityAddress") String cityAddress,
        @JsonProperty("minimumRank") int minimumRank,
        @JsonProperty("slotsBooked") int slotsBooked,
        @JsonProperty("allSlots") int allSlots,
        @JsonProperty("usersReserved") Set<UserDetails> userDetailsSet
) {
    public static ReservationResponse fromReservation(Reservation reservation){
        return new ReservationResponse(
                reservation.getId(),
                reservation.getUserCreator().getId(),
                reservation.getDateTimeFrom(),
                reservation.getDateTimeTo(),
                reservation.getSubject(),
                reservation.getCityAddress(),
                reservation.getMinimumRank(),
                reservation.getSlotsBooked(),
                reservation.getAllSlots(),
                reservation.getUsersReserved()
        );
    }
}
