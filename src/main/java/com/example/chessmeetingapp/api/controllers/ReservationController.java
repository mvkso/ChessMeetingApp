package com.example.chessmeetingapp.api.controllers;

import com.example.chessmeetingapp.api.response.MessageResponse;
import com.example.chessmeetingapp.api.response.ReservationResponse;
import com.example.chessmeetingapp.api.response.UserDetailsResponse;
import com.example.chessmeetingapp.entities.Reservation;
import com.example.chessmeetingapp.error.RestException;
import com.example.chessmeetingapp.requests.reservation.BookReservation;
import com.example.chessmeetingapp.requests.reservation.CreateReservationRequest;
import com.example.chessmeetingapp.services.ReservationService;
import com.example.chessmeetingapp.services.UserDetailsService;
import com.example.chessmeetingapp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final ReservationService reservationService;
    private final UserDetailsService userDetailsService;
//    private final UserDetailsService userDetailsService;
//    private final SchedulerJobManager schedulerJobManager;


    @PersistenceContext
    EntityManager em;
    @Value("${adminDefaultEmail}")
    private String adminDefaultEmail;
//    @Value("${reportPath}")
//    private String reportPath;



    @Autowired
    public ReservationController(ReservationService reservationService, UserDetailsService userDetailsService) {
        this.reservationService = reservationService;
        this.userDetailsService = userDetailsService;

    }


    @DeleteMapping("/{reservationId}")
    public void deleteReservation(@PathVariable("reservationId") int Id){
        reservationService.deleteReservation(Id)
                .orElseThrow(() -> new RestException("Unable to delete reservation"));
    }

    @GetMapping("/{reservationId}")
    public Reservation getReservationById(@PathVariable("reservationId") int Id){
        return reservationService.getReservationById(Id).get();
    }

    @PostMapping("/")
    public ResponseEntity<?> createReservation(@RequestBody CreateReservationRequest request){
        reservationService.createReservation(request);
        return ResponseEntity.ok(new MessageResponse("Reservation creation accomplished!"));
    }

    @GetMapping("/")
    public List<ReservationResponse> getAllReservations(){
        return reservationService.readAllReservations()
                .stream().map(ReservationResponse::fromReservation)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{userDetailsId}/booked")
    public List<ReservationResponse> getAllBookedReservations(@PathVariable("userDetailsId") int Id){
        return reservationService.getAllBookedReservations(Id)
                .stream().map(ReservationResponse::fromReservation)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{userDetailsId}/created")
    public List<ReservationResponse> getAllCreatedReservations(@PathVariable("userDetailsId") int Id){
        return reservationService.getAllCreatedReservations(Id)
                .stream().map(ReservationResponse::fromReservation)
                .collect(Collectors.toList());
    }

    @GetMapping("/{reservationId}/bookedUsers")
    public List<UserDetailsResponse> getAllUsersFromReservation(@PathVariable("reservationId") int Id){
        return reservationService.getReservationById(Id).get().getUsersReserved()
                .stream().map(UserDetailsResponse::fromUserDetails)
                .collect(Collectors.toList());
    }

    @GetMapping("/city/{cityAddress}/user/{userId}")
    public List<ReservationResponse> getAllReservationsByCityAddress(@PathVariable("cityAddress") String cityAddress, @PathVariable("userId") int userId){

        return reservationService.getAllReservationsByCityAddress(cityAddress,userId)
                .stream().map(ReservationResponse::fromReservation)
                .collect(Collectors.toList());
    }

    @PutMapping("/cancel/{reservationId}")
    public ResponseEntity<?>  cancelReservation(@Valid @RequestBody BookReservation reservation, @PathVariable("reservationId") int reservationId){
        reservationService.cancelReservation(reservation.reservationId(), reservation.userId())
                .orElseThrow(() -> new RestException("Unable to cancel reservation"));
        return ResponseEntity.ok(new MessageResponse("Reservation cancel or deleted"));
    }

    @PutMapping("/book/{reservationId}")
    public ResponseEntity<?>  bookReservation(@Valid @RequestBody BookReservation reservation, @PathVariable("reservationId") int reservationId){
        reservationService.bookReservation(reservation.reservationId(), reservation.userId())
                .orElseThrow(()-> new RestException("Unable to book reservation"));
        return ResponseEntity.ok(new MessageResponse("Reservation booked"));
    }

    @GetMapping("/recent")
    public List<ReservationResponse> getMostRecentReservations(){
        return reservationService.getTwoMostRecentReservations()
                .stream().map(ReservationResponse::fromReservation)
                .collect(Collectors.toList());
    }





}
