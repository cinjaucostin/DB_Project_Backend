package com.example.backendglobaldirectory.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Utils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static Optional<LocalDateTime> convertDateStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        LocalDate date = LocalDate.parse(dateString, formatter);
        LocalTime time = LocalTime.of(0, 0);

        LocalDateTime dateTime = LocalDateTime.of(date, time);

        LocalDateTime currentTime = LocalDateTime.now();

        if(dateTime.isAfter(currentTime)) {
            return Optional.empty();
        }

        return Optional.of(dateTime);
    }

}
