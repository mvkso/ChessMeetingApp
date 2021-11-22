package com.example.chessmeetingapp;

import com.example.chessmeetingapp.repositories.UserDetailsRepository;
import com.example.chessmeetingapp.services.UserDetailsService;
import com.example.chessmeetingapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan("com.example.chessmeetingapp")
public class ChessMeetingApp {

    public static void main(String[] args) {

        SpringApplication.run(ChessMeetingApp.class, args);

    }


}
