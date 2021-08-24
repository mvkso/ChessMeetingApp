package com.example.chessmeetingapp.services;

import com.example.chessmeetingapp.repositories.ReservationsRepository;
import com.example.chessmeetingapp.repositories.UserDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationsRepository reservationsRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final UserService userService;

    public static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    public ReservationService(ReservationsRepository reservationsRepository, UserDetailsRepository userDetailsRepository, UserService userService) {
        this.reservationsRepository = reservationsRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.userService = userService;
    }
}
