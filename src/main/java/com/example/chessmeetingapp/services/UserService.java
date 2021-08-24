package com.example.chessmeetingapp.services;

import com.example.chessmeetingapp.entities.User;
import com.example.chessmeetingapp.repositories.UserRepository;
import com.example.chessmeetingapp.requests.ChangeUserEmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Integer id){
        try {
            return userRepository.findById(id).get();
        }catch (Exception ex){
            return null;
        }
    }

    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


    @Transactional
    public boolean existsByEmail(String email){
        if(userRepository.existsByEmail(email)){
            return true;
        }
        return false;
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findUserById(int id){
        return userRepository.findById(id);
    }

    @Transactional
    public Optional<Boolean> updateEmail(User updatedUser, ChangeUserEmailRequest request) {
        updatedUser.setEmail(request.email());
        try{
            em.merge(updatedUser);
            return Optional.of(true);
        }catch (Exception e){
            return Optional.of(false);
        }
    }








}
