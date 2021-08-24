package com.example.chessmeetingapp.repositories;

import com.example.chessmeetingapp.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
