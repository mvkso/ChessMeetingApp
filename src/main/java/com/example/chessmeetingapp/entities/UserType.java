package com.example.chessmeetingapp.entities;

import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;

public enum UserType implements GrantedAuthority {
    ADMIN("Admin"),
    USER("User");

    private final String value;

    UserType(String value){this.value = value;}

    public static Optional<UserType> from(String value) {
        return switch (value.toUpperCase()) {
            case "ADMIN" -> Optional.of(UserType.ADMIN);
            case "USER" -> Optional.of(UserType.USER);
            default -> Optional.empty();
        };
    }

    public String value() {
        return value;
    }


    @Override
    public String getAuthority() {
        return name();
    }

}
