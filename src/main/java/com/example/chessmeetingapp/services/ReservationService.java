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
import java.util.stream.Collectors;

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
            reservation = new Reservation(userDetails,request.timeFrom(), request.timeTo(), request.Subject(), request.CityAddress().toLowerCase(Locale.ROOT), request.MinimumRank(), request.Slots());
            reservation.getUsersReserved().add(userDetails);
            userDetails.getCreatedReservations().add(reservation);
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
    @Transactional
    public Optional<Boolean> cancelReservation(int reservationId, int userId) {
        try {
            Reservation reservation = reservationsRepository.findById(reservationId).get();
            UserDetails userDetails = userDetailsService.getUserDetailsByUserId(userId).get();
            if (userDetails.getId() == reservation.getUserCreator().getId()) {
                for(UserDetails userDetailsReservation : reservation.getUsersReserved()){
                    userDetailsReservation.removeBookedReservation(reservation);
                    em.merge(userDetailsReservation);
                }
                userDetails.getCreatedReservations().remove(reservation);
                em.merge(userDetails);
                reservationsRepository.delete(reservation);
                logger.warn("event id:" + reservation.getId() + " will be deleted by userDetails id: " + userDetails.getId());
                return Optional.of(true);
            } else {
                if (reservation.getUsersReserved().contains(userDetails)) {
                    reservation.setSlotsBooked(reservation.getSlotsBooked() - 1);
                    userDetails.removeBookedReservation(reservation);
                    em.merge(userDetails);
                    return Optional.of(true);
                } else return Optional.of(false);
            }
        } catch (NoSuchElementException ex) {
            logger.warn("No such element exception in cancel function");
            return Optional.of(false);
        } catch (Exception ex) {
            logger.warn("exception in cancel function");
            return Optional.of(false);
        }
    }

    @Transactional
    public Optional<Boolean> bookReservation(int reservationId, int userId){
        try{
            Reservation reservation = reservationsRepository.findById(reservationId).get();
            UserDetails userDetails = userDetailsService.getUserDetailsByUserId(userId).get();
            if(userDetails.getId() == reservation.getUserCreator().getId()){
                logger.warn("Cannot book event id:"+reservation.getId()+" by userDetails id: "+userDetails.getId());
                return Optional.of(false);
            }else {
                if(reservation.getAllSlots() > reservation.getSlotsBooked() && !userDetails.getCreatedReservations().contains(reservation)) {
                    reservation.setSlotsBooked(reservation.getSlotsBooked()+1);
                    userDetails.addBookedReservation(reservation);
                    em.merge(userDetails);
                    return Optional.of(true);
                }else return Optional.of(false);
            }
        }catch (NoSuchElementException ex){
            logger.warn("No such element exception in bookReservation function");
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



    public List<Reservation> getAllCreatedReservations(int userId){
        var result = new ArrayList<Reservation>();
        UserDetails userDetails;
        try{
            userDetails = userDetailsRepository.findById(userId).get();
//            reservationsRepository.findAllByUserCreator(userDetails).forEach(result::add);
            userDetails.getCreatedReservations().forEach(result::add);
        }catch(NoSuchElementException e){
            logger.warn("no user with id "+userId);
            return List.of();
        }
        return result;
    }


    public List<Reservation> getAllBookedReservations(int userId){
        var result = new ArrayList<Reservation>();
        UserDetails userDetails;
        try{
            userDetails = userDetailsRepository.findById(userId).get();
            userDetails.getReservations().forEach(result::add);
        }catch(NoSuchElementException e){
            logger.warn("no user with id "+userId);
            return List.of();
        }

        return result;
    }

    public List<Reservation> getAllReservationsByCityAddress(String cityAddress, int id){
        List<Reservation> reservationsList;
        UserDetails userDetails;
        try {
            userDetails = userDetailsRepository.findUserDetailsByUser_UserId(id).get();
            reservationsList = reservationsRepository.findAllByCityAddress(cityAddress.toLowerCase(Locale.ROOT));

            Iterator<Reservation> i = reservationsList.iterator();
            while (i.hasNext()) {
                Reservation reservation = i.next();
                if(reservation.getUserCreator().equals(userDetails) || reservation.getUsersReserved().contains(userDetails)){
                    i.remove();
                }
            }
//                    .forEach(reservation -> {
//                        if(reservation.getUserCreator().equals(userDetails) || reservation.getUsersReserved().contains(userDetails)){
//
//                        }
//                    });



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
