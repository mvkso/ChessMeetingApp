package com.example.chessmeetingapp.services;

import com.example.chessmeetingapp.entities.User;
import com.example.chessmeetingapp.entities.UserDetails;
import com.example.chessmeetingapp.entities.UserType;
import com.example.chessmeetingapp.repositories.UserDetailsRepository;
import com.example.chessmeetingapp.repositories.UserRepository;
import com.example.chessmeetingapp.requests.usersData.EditUserDetailsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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

    public List<UserDetails> getUsersDetails(){
        List<UserDetails> userDetailsList = new LinkedList<>();
        userDetailsRepository.findAll().forEach(userDetailsList::add);
        return userDetailsList;
    }

    @Transactional
    public Optional<Boolean> editEmployee(EditUserDetailsRequest request, int employeeId) {
        UserDetails userDetails;
        try {
            userDetails = userDetailsRepository.findById(employeeId).get();

        } catch (NoSuchElementException e) {
            return Optional.of(false);
        }
        userDetails.setFirstName(request.firstName());
        userDetails.setLastName(request.lastName());
        userDetails.setPhoneNumber(request.newPhoneNumber());
        userDetails.getUser().setEmail(request.newEmail());
        try{
            em.merge(userDetails);
            return Optional.of(true);
        }catch (Exception e){
            return Optional.of(false);
        }

    }

    public Optional<UserDetails> getUserDetailsById(int userDetailsId) {
        UserDetails userDetails;
        try {
            userDetails = userDetailsRepository.findById(userDetailsId).get();
            return Optional.of(userDetails);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public Optional<UserDetails> getUserDetailsByUserId(int userId) {
        try {
            return userDetailsRepository.findUserDetailsByUser_UserId(userId);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return userDetailsRepository.existsByPhoneNumber(phoneNumber);
    }

    public void save(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
    }

    @Transactional
    public Optional<Boolean> changePassword(String password, int userId) {
        User user;
        try {
            user = userRepository.findById(userId).get();
        } catch (NoSuchElementException e) {
            return Optional.of(false);
        }
        user.setPassword(password);
        try {
            em.merge(user);
            return Optional.of(true);
        } catch (Exception e) {
            return Optional.of(false);
        }
    }

        @Transactional
        public void changePhoneNumber(String phoneNumber, int Id) throws NoSuchElementException {
            UserDetails userDetails = userDetailsRepository.findUserDetailsByUser_UserId(Id).get();
            userDetails.setPhoneNumber(phoneNumber);
        }



    @Transactional
    public void changeFirstName(String firstName, int Id) {
        UserDetails userDetails;
        try {
            userDetails = userDetailsRepository.findUserDetailsByUser_UserId(Id).get();

        } catch (NoSuchElementException e) {
            return;
        }
        userDetails.setFirstName(firstName);

    }

    @Transactional
    public void changeLastName(String lastName, int Id) {
        UserDetails userDetails;
        try {
            userDetails = userDetailsRepository.findUserDetailsByUser_UserId(Id).get();

        } catch (NoSuchElementException e) {
            return;
        }
        userDetails.setLastName(lastName);

    }





    public String getPassword(int userDetailsId) {
        UserDetails userDetails;
        try {
            userDetails = userDetailsRepository.findById(userDetailsId).get();

        } catch (NoSuchElementException e) {
            return null;
        }
        return userDetails.getUser().getPassword();
    }

    public Optional<UserDetails> findEmployeeByUserEmail(String email){
        return userDetailsRepository.findByUser_Email(email);
    }

    public boolean isAdminExist(){
        AtomicBoolean isExist = new AtomicBoolean(false);
        userRepository.findAll().forEach(user -> {
            if(user.getUserType() == UserType.ADMIN){
                isExist.set(true);
            }
        });
        return isExist.get();
    }
//
//    public Page<UserDetails> getUserDetailsByPage(int page, int size){
//        Pageable usersDetails= PageRequest.of(page, size, Sort.by("lastName").and(Sort.by("firstName")));
//        return userDetailsRepository.findAll(usersDetails);
//    }







}
