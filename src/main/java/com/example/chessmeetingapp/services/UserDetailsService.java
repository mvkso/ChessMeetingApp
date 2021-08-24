package com.example.chessmeetingapp.services;

import com.example.chessmeetingapp.entities.UserDetails;
import com.example.chessmeetingapp.repositories.UserDetailsRepository;
import com.example.chessmeetingapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserDetailsService {

    private UserDetailsRepository userDetailsRepository;
    private UserRepository userRepository;


    @PersistenceContext
    EntityManager em;

    public UserDetailsService(UserDetailsRepository userDetailsRepository, UserRepository userRepository) {
        this.userDetailsRepository = userDetailsRepository;
        this.userRepository = userRepository;
    }

    public List<UserDetails> getUserDetails(){
        List<UserDetails> userDetailsList = new LinkedList<>();
        userDetailsRepository.findAll().forEach(userDetailsList::add);
        return userDetailsList;
    }

    

}
