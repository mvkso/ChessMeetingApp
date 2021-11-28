package com.example.chessmeetingapp.api.controllers;


import com.example.chessmeetingapp.api.response.MessageResponse;
import com.example.chessmeetingapp.api.response.UserDetailsResponse;
import com.example.chessmeetingapp.entities.Reservation;
import com.example.chessmeetingapp.entities.UserDetails;
import com.example.chessmeetingapp.error.RestException;
import com.example.chessmeetingapp.requests.usersData.ChangePasswordRequest;
import com.example.chessmeetingapp.requests.usersData.EditNameOrLastNameRequest;
import com.example.chessmeetingapp.requests.usersData.EditPhoneNumberRequest;
import com.example.chessmeetingapp.requests.usersData.EditUserDetailsRequest;
import com.example.chessmeetingapp.services.UserDetailsService;
import com.example.chessmeetingapp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


import static com.example.chessmeetingapp.error.ErrorMessage.errorMessage;

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
        System.out.println(userDetailsService.getUsersDetails());
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




    @GetMapping("/{userDetailsId}")
//    @PreAuthorize("hasAuthority('Admin')")
    @Transactional
    public UserDetailsResponse getUserDetailsById(@PathVariable("userDetailsId") int userDatailsId){
        System.out.println(userDetailsService.getUserDetailsById(userDatailsId).get().getCreatedReservations().toString());
        return userDetailsService.getUserDetailsById(userDatailsId)
                .map(UserDetailsResponse::fromUserDetails)
                .orElseThrow(() -> new RestException("Unable to get employee by employeeId=" + userDatailsId));
    }

    @GetMapping("/userId/{userId}")
//    @PreAuthorize("hasAuthority('User')")
    @Transactional
    public UserDetailsResponse getUserDetailsByUserId(@PathVariable("userId") int userId){
        return userDetailsService.getUserDetailsByUserId(userId)
                .map(UserDetailsResponse::fromUserDetails)
                .orElseThrow(() -> new RestException("Unable to get employee by userId=" + userId));
    }

//    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/{userDetailsId}")
    @Transactional
    public ResponseEntity<?> updateUserDetails(@Valid @RequestBody EditUserDetailsRequest editUserDetailsRequest,
                                            @PathVariable int userDetailsId) {

        if (newEmailAlreadyExists(editUserDetailsRequest)) {
            return errorMessage("Error: Email is already taken!");
        }

        if (newPhoneNumberAlreadyExists(editUserDetailsRequest)) {
            return errorMessage("Error: Phone number is already taken!");
        }

        userDetailsService.editEmployee(editUserDetailsRequest,userDetailsId);
        //schedulerJobManager.setupTriggers();
        return ResponseEntity.ok(new MessageResponse("Employee edited successfully!"));
    }

  //  @PreAuthorize("hasAuthority('User')")
    @PutMapping("/{userId}/phoneNumber")
    public ResponseEntity<?> updatePhoneNumber(@Valid @RequestBody EditPhoneNumberRequest request,
                                                  @PathVariable int userId){
        try{
            userDetailsService.changePhoneNumber(request.newPhoneNumber(), userId);
          //  schedulerJobManager.setupTriggers();
            return ResponseEntity.ok(new MessageResponse("Phone number of user with id= " + userId + " edited successfully!"));
        }
        catch (NoSuchElementException ex){
            return errorMessage("Cannot find employee with userId=" + userId);
        }

    }


    //@PreAuthorize("hasAuthority('User')")
    @PutMapping("/{userId}/nameOrLastName")
    public ResponseEntity<?> updateUserNameOrLastName(@Valid @RequestBody EditNameOrLastNameRequest request,
                                                          @PathVariable int userId){
        UserDetails userDetails;
        try{
            userDetails = userDetailsService.getUserDetailsByUserId(userId).get();
        }catch (NoSuchElementException e){
            return errorMessage("Error: Employee not found");
        }
        if(!request.name().equals(userDetails.getFirstName())){
            userDetailsService.changeFirstName(request.name(), userId);
        }
        if(!request.lastName().equals(userDetails.getLastName())){
            userDetailsService.changeLastName(request.lastName(),userId);
        }
        return ResponseEntity.ok(new MessageResponse("Employee data edited successfully!"));
    }


    @PutMapping("{userId}/password")
    //@PreAuthorize("hasAuthority('User') or hasAuthority('Admin')")
    public ResponseEntity<?> editUserPassword(@RequestBody ChangePasswordRequest request,
                                                  @PathVariable("userId") int userId){

        if(!request.newPassword().equals(request.confirmedNewPassword())){
            return errorMessage("Error: Passwords are not the same!");
        }
        String email;
        try{
            email = userService.getUserById(userId).getEmail();
        }
        catch(NoSuchElementException e){
            return errorMessage("Error: Not found employee!");
        }
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.oldPassword()));
        }
        catch(Exception e){
            return errorMessage("Error: Old password is wrong!");
        }
        userDetailsService.changePassword(encoder.encode(request.newPassword()),userId);

        return ResponseEntity.ok(new MessageResponse("Password edited successfully!"));
    }

//
//    @DeleteMapping("/{userDetailsId}")
////    @PreAuthorize("hasAuthority('Admin')")
//    public void deleteUserDetails(@PathVariable("userDetailsId") int userDetailsId){
//        userDetailsService.delete(userDetailsId)
//                .orElseThrow(() -> new RestException("Unable to delete an user"));
//    }


}
