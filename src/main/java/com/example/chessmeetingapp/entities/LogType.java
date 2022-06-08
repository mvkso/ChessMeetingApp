package com.example.chessmeetingapp.entities;

import java.util.Optional;

public enum LogType {
    INFO("info"),
    WARN("warn"),
    ERROR("error");

    private final String value;

    LogType(String value) {
        this.value = value;
    }

    public static Optional<LogType> from(String value) {
        return switch (value) {
            case "info" -> Optional.of(LogType.INFO);
            case "warn" -> Optional.of(LogType.WARN);
            case "error" -> Optional.of(LogType.ERROR);
            default -> Optional.empty();
        };
    }
    public String getValue() {
        return value;
    }
}
