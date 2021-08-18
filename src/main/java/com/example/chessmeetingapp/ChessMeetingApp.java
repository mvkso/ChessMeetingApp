package com.example.chessmeetingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ChessMeetingApp {

    public static void main(String[] args) {
        SpringApplication.run(ChessMeetingApp.class, args);
    }

}
