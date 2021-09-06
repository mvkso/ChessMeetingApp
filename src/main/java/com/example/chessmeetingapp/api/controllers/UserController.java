package com.example.chessmeetingapp.api.controllers;

import com.example.chessmeetingapp.api.response.MessageResponse;
import com.example.chessmeetingapp.api.response.UserResponse;
import com.example.chessmeetingapp.entities.User;
import com.example.chessmeetingapp.requests.usersData.ChangeUserEmailRequest;
import com.example.chessmeetingapp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @CrossOrigin
    public ResponseEntity<List<UserResponse>> findAllUsers(){
        logger.info("exposing all users");
        var result = new ArrayList<UserResponse>();
        userService.getAllUsers().forEach(user -> {
            result.add(UserResponse.fromUser(user));
        });
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{userId}")
    @CrossOrigin
    public ResponseEntity<UserResponse> findUserById(@PathVariable Integer userId){
        try {
            return ResponseEntity.ok(UserResponse.fromUser(userService.getUserById(userId)));
        }catch (Exception ex){
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{userId}/email")
    //@PreAuthorize("hasAuthority('Admin') or hasAuthority('Employee')")
    @Transactional
    public ResponseEntity<?> updateUser(@PathVariable Integer userId, @RequestBody ChangeUserEmailRequest request) {
        System.out.println("update User was called.");
        try{
            User updatedUser = userService.findUserById(userId).get();
            System.out.println("Email: "+request.email());
            userService.updateEmail(updatedUser, request);
            return ResponseEntity.ok(new MessageResponse("Email updated successfully!"));

        }
        catch(NoSuchElementException ex){
            return ResponseEntity.badRequest().body(new MessageResponse("User with id=" +userId + " cannot be found." ));
        }

    }

    @GetMapping("/email")
    @CrossOrigin
    public ResponseEntity<UserResponse> findUserByEmail(@RequestParam String email){
        try {
            return ResponseEntity.ok(UserResponse.fromUser(userService.getUserByEmail(email).get()));
        }catch (Exception ex){
            return ResponseEntity.notFound().build();
        }
    }



}
