package com.example.chessmeetingapp.api.controllers;

import com.example.chessmeetingapp.api.response.JwtResponse;
import com.example.chessmeetingapp.api.response.MessageResponse;
import com.example.chessmeetingapp.entities.User;
import com.example.chessmeetingapp.entities.UserDetails;
import com.example.chessmeetingapp.entities.UserType;
import com.example.chessmeetingapp.requests.signInUp.SigninRequest;
import com.example.chessmeetingapp.requests.signInUp.SignupRequest;
import com.example.chessmeetingapp.security.jwt.JwtUtils;
import com.example.chessmeetingapp.security.services.UserDetailsImpl;
import com.example.chessmeetingapp.services.UserDetailsService;
import com.example.chessmeetingapp.services.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController implements InitializingBean {

    @Value("${adminDefaultEmail}")
    private String adminDefaultEmail;
    @Value("${adminDefaultPassword}")
    private String adminDefaultPassword;

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private UserDetailsService userDetailsService;
    private PasswordEncoder encoder;
    private JwtUtils jwtUtils;

    private void createInitialAdminAccount(){

        if(!userDetailsService.isAdminExist()){
            userService.save(new User(adminDefaultEmail,
                    encoder.encode(adminDefaultPassword)));
        }
    }

    @Override
    public void afterPropertiesSet() {
        createInitialAdminAccount();
    }

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService,
                          UserDetailsService userDetailsService, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @CrossOrigin
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest loginRequest){
        System.out.println("remove this !");
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        }catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error : Email or password is wrong"));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Object[] listOfAuthorities = userDetails.getAuthorities().toArray();
        UserType userType = UserType.from(String.valueOf(listOfAuthorities[0])).get();
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                userType));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if(!signUpRequest.getPassword().equals(signUpRequest.getConfirmedPassword())){
            return errorMessage("Error: Passwords are wrong!");
        }

        if(signUpRequest.getPassword().length() < 5){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Password too short, at least 5 characters!"));
        }


        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return errorMessage("Error: Email is already taken!");
        }

        if (userDetailsService.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
            return errorMessage("Error: Phone number is already taken!");
        }

        User user = new User(signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        String userType = signUpRequest.getUserType();

        if (userType == null) {
            user.setUserType(UserType.USER);
        }else{
            switch (userType.toUpperCase()) {
                case "ADMIN" -> user.setUserType(UserType.ADMIN);
                case "USER" -> user.setUserType(UserType.USER);
                default -> {
                    user.setUserType(UserType.USER);
                    throw new NoSuchElementException("There's no employee of type " + userType.toUpperCase());
                }
            }
        }
        if(user.getUserType() == UserType.USER) {
            UserDetails userDetails = new UserDetails(signUpRequest.getName(), signUpRequest.getLastName(),
                    signUpRequest.getPhoneNumber(),user);
            userDetailsService.save(userDetails);
        }
        else{
            userService.save(user);
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private ResponseEntity<?> errorMessage(String message) {
        return ResponseEntity.badRequest().body(new MessageResponse(message));
    }


}
