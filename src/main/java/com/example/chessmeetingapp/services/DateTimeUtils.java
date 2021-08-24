package com.example.chessmeetingapp.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateTimeUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter
            .ofPattern("kk:mm");

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd kk:mm");

    private DateTimeUtils() {
    }

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static LocalTime parseTime(String time) {
        return LocalTime.parse(time, TIME_FORMATTER);
    }

    public static String formatTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }


    public static String formatDbTime(LocalTime time) {
        return time.format(TIME_FORMATTER) + ":00";
    }

    public static LocalDateTime parseDateTime(String time) {
        return LocalDateTime.parse(time, DATE_TIME_FORMATTER);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    public static Date fromLocalDateTime(LocalDateTime dateTime){
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
