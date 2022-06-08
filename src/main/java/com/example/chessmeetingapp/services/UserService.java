package com.example.chessmeetingapp.services;

import com.example.chessmeetingapp.entities.Log;
import com.example.chessmeetingapp.entities.LogType;
import com.example.chessmeetingapp.entities.User;
import com.example.chessmeetingapp.repositories.UserRepository;
import com.example.chessmeetingapp.requests.usersData.ChangeUserEmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LogService logService;

    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @PersistenceContext
    EntityManager em;

    @Autowired
    public UserService(UserRepository userRepository, LogService logService) {
        this.userRepository = userRepository;
        this.logService = logService;
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
        logService.saveLog(new Log(LogType.INFO, "User id "+user.getUserId()+" saved"));

    }

    public Optional<User> findUserById(int id){
        return userRepository.findById(id);
    }

    @Transactional
    public Optional<Boolean> updateEmail(User updatedUser, ChangeUserEmailRequest request) {
        updatedUser.setEmail(request.email());
        try{
            em.merge(updatedUser);
            logService.saveLog(new Log(LogType.INFO, "User id "+updatedUser.getUserId()+" updated his email to: "+request.email()));
            return Optional.of(true);
        }catch (Exception e){
            return Optional.of(false);
        }
    }








}
