package com.example.chessmeetingapp.error;

import com.example.chessmeetingapp.api.response.MessageResponse;
import org.springframework.http.ResponseEntity;

public class ErrorMessage {
    public static ResponseEntity<?> errorMessage(String message){
        return ResponseEntity.badRequest().body(new MessageResponse(message));
    }
}
