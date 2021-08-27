package com.example.chessmeetingapp.repositories;

import com.example.chessmeetingapp.entities.User;
import com.example.chessmeetingapp.entities.UserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends CrudRepository<UserDetails, Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<UserDetails> findByUser_Email(String email);

    Optional<UserDetails> findUserDetailsByUser_UserId(int id);

}
