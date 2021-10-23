package com.example.chessmeetingapp.services;

import com.example.chessmeetingapp.entities.Reservation;
import com.example.chessmeetingapp.entities.UserDetails;
import com.example.chessmeetingapp.repositories.ReservationsRepository;
import com.example.chessmeetingapp.repositories.UserDetailsRepository;
import com.example.chessmeetingapp.requests.reservation.CreateReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.*;
import java.util.*;

@Service
public class ReservationService {

    private final ReservationsRepository reservationsRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @PersistenceContext
    EntityManager em;

    public static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    public ReservationService(ReservationsRepository reservationsRepository, UserDetailsRepository userDetailsRepository, UserService userService, UserDetailsService userDetailsService) {
        this.reservationsRepository = reservationsRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    public Optional<Boolean> createReservation(CreateReservationRequest request){
        UserDetails userDetails;
        Reservation reservation;
        try{
            userDetails = userDetailsService.getUserDetailsByUserId(request.userId()).get();

        }catch (NoSuchElementException e){
            return Optional.of(true);
        }
        try{
            reservation = new Reservation(userDetails,request.timeFrom(), request.timeTo(), request.Subject(), request.CityAddress(), request.MinimumRank(), request.Slots());
            reservation.getUsersReserved().add(userDetails);
            reservationsRepository.save(reservation);
            return Optional.of(false);
        }catch (Exception e){
            return Optional.empty();
        }

    }

    @Transactional
    public Optional<Boolean> deleteReservation(int reservationId){
        Reservation reservation;
        try {
            reservation = reservationsRepository.findById(reservationId).get();
        }catch (NoSuchElementException e){
            logger.warn("exception in deleteReservation function");
            return Optional.of(false);
        }
        try {
            reservationsRepository.delete(reservation);

            return Optional.of(true);
        }catch (Exception e){
            logger.warn("exception in deleteReservation function");
            return Optional.of(false);
        }
    }

    public Optional<Boolean> cancelReservation(int reservationId, int userId){
        Reservation reservation = null;
        try{
            reservation = reservationsRepository.findById(reservationId).get();
        }catch (NoSuchElementException e){
            logger.warn("Exception in cancelReservation function. There might be no reservations with this id!");
        }
        try{
            UserDetails userDetails = userDetailsService.getUserDetailsByUserId(userId).get();
            assert reservation != null;
            reservation.getUsersReserved().remove(userDetails);
            em.merge(reservation);
            return Optional.of(true);
        }catch (Exception e){
            logger.warn("exception in cancelReservation function");
            return Optional.of(false);
        }
    }

    public Optional<Boolean> bookReservation(int reservationId, int userId){
        try{
            Reservation reservation = reservationsRepository.findById(reservationId).get();
            UserDetails userDetails = userDetailsService.getUserDetailsByUserId(userId).get();
            reservation.getUsersReserved().add(userDetails);
            em.merge(reservation);
            return Optional.of(true);
        }catch (NoSuchElementException ex){
            logger.warn("No such element exception in bookReservation function");
            return Optional.of(false);
        }catch (Exception ex){
            logger.warn("Exception in bookReservation function");
            return Optional.of(false);
        }
    }

    private LocalDateTime fromLocalToUTC(LocalDateTime localTime){
        ZonedDateTime currentTimeZone=localTime.atZone(ZoneId.systemDefault());
        return LocalDateTime.ofInstant(Instant.from(currentTimeZone), ZoneOffset.UTC);
    }

    public List<Reservation> readAllReservations(){
        var result = new ArrayList<Reservation>();
        reservationsRepository.findAll().forEach(result::add);
        return result;
    }

    public Optional<Reservation> getReservationById(int Id){
        return reservationsRepository.findById(Id);
    }

    public Set<Reservation> getAllCreatedReservations(int userId){
        UserDetails userDetails;
        try{
            userDetails = userDetailsRepository.findUserDetailsByUser_UserId(userId).get();
        }catch(NoSuchElementException e){
            logger.warn("no user with id "+userId);
            return Set.of();
        }
        return userDetails.getCreatedReservations();
    }


    public Set<Reservation> getAllBookedReservations(int userId){
        UserDetails userDetails;
        try{
            userDetails = userDetailsRepository.findUserDetailsByUser_UserId(userId).get();
        }catch(NoSuchElementException e){
            logger.warn("no user with id "+userId);
            return Set.of();
        }
        return userDetails.getReservations();
    }

    public List<Reservation> getAllReservationsByCity(String address){
        List<Reservation> reservationsList;
        try {
            reservationsList = reservationsRepository.findAllByAddress(address);
            return reservationsList;
        }catch (NoSuchElementException ex){
            logger.warn("exception in getAllReservationsByCity function");
            return List.of();
        }catch (Exception ex){
            logger.warn("exception in getAllReservationsByCity function");
            return List.of();
        }



    }

    public List<Reservation> getUpcomingReservations(int userId, LocalDateTime nowTime){
        List<Reservation> upcomingReservations = new LinkedList<>();
        try{
            for(Reservation reservation : reservationsRepository.findAllByUserCreator_Id(userId)){
                if (reservation.getDateTimeFrom().isAfter(nowTime)) {
                    upcomingReservations.add(reservation);
                }
            }
//            reservations.addAll();
        }catch (NoSuchElementException ex){
            logger.warn("noSuchElementException in getUpcomingReservations function");
            return List.of();
        }

        return upcomingReservations;
    }

    public List<Reservation> getPastReservations(int userId, LocalDateTime nowTime){
        List<Reservation> pastReservations = new LinkedList<>();
        try{
            for(Reservation reservation : reservationsRepository.findAllByUserCreator_Id(userId)){
                if (reservation.getDateTimeFrom().isBefore(nowTime)) {
                    pastReservations.add(reservation);
                }
            }
//            reservations.addAll();
        }catch (NoSuchElementException ex){
            logger.warn("noSuchElementException in getUpcomingReservations function");
            return List.of();
        }

        return pastReservations;
    }










}
