package com.example.chessmeetingapp.api;


import com.example.chessmeetingapp.api.response.UserDetailsResponse;
import com.example.chessmeetingapp.entities.UserDetails;
import com.example.chessmeetingapp.requests.usersData.EditUserDetailsRequest;
import com.example.chessmeetingapp.services.UserDetailsService;
import com.example.chessmeetingapp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/userDetails")
public class UserDetailsController {


    private Logger logger = LoggerFactory.getLogger(UserDetailsController.class);
    private UserDetailsService userDetailsService;
    private UserService userService;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    //private SchedulerJobManager schedulerJobManager;


    private boolean newEmailAlreadyExists(EditUserDetailsRequest editUserDetailsRequest) {
        return !editUserDetailsRequest.oldEmail().equals(editUserDetailsRequest.newEmail())
                && userService.existsByEmail(editUserDetailsRequest.newEmail());
    }

    private boolean newPhoneNumberAlreadyExists(EditUserDetailsRequest editUserDetailsRequest) {
        return !editUserDetailsRequest.oldPhoneNumber().equals(editUserDetailsRequest.newPhoneNumber())
                && userDetailsService.existsByPhoneNumber(editUserDetailsRequest.newPhoneNumber());
    }


    @Autowired
    public UserDetailsController(UserDetailsService userDetailsService, UserService userService, PasswordEncoder passwordEncoder,
                              AuthenticationManager authenticationManager
                              /*,SchedulerJobManager schedulerJobManager */) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.encoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        //this.schedulerJobManager = schedulerJobManager;
    }


    @GetMapping("/")
    //@PreAuthorize("hasAuthority('Admin') or hasAuthority('User')")
    public List<UserDetailsResponse> getAllEmployees(){

        return userDetailsService.getUsersDetails()
                .stream()
                .map(UserDetailsResponse::fromUserDetails)
                .collect(Collectors.toList());
    }

//    @GetMapping("/page/{pageNumber}")
//    //@PreAuthorize("hasAuthority('Admin')")
//    @Transactional
//    public List<UserDetailsResponse> getEmployeesByPage(@PathVariable("pageNumber") int pageNumber){
//        return userDetailsService.getUserDetailsByPage(pageNumber,10)
//                .stream()
//                .map(UserDetailsResponse::fromUserDetails)
//                .collect(Collectors.toList());
//    }





}
