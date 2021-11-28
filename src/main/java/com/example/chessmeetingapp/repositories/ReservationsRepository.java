package com.example.chessmeetingapp.repositories;

import com.example.chessmeetingapp.entities.Reservation;
import com.example.chessmeetingapp.entities.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ReservationsRepository extends CrudRepository<Reservation, Integer> {

    List<Reservation> findAllByDateTimeFromBetween(LocalDateTime timeFrom, LocalDateTime timeTo);

    List<Reservation> findAllByUserCreator_Id(int id);

    List<Reservation> findAllByUserCreator(UserDetails userDetails);

    List<Reservation> findAllByDateTimeFrom(LocalDateTime localDateTime);

    List<Reservation> findAllByCityAddress(String address);





}
