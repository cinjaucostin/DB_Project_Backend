package com.example.backendglobaldirectory.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Utils {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static LocalDateTime convertDateStringToLocalDateTime(String dateString) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        LocalDate date = LocalDate.parse(dateString, formatter);
        LocalTime time = LocalTime.of(0, 0);

        LocalDateTime dateTime = LocalDateTime.of(date, time);

        System.out.println(dateTime);

        return dateTime;

    }
}
