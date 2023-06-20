package com.example.backendglobaldirectory.utils;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Utils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String ANNIVERSARY_EMAIL_PATTERN_PATH = "src/main/resources/anniversary_mail_pattern.txt";

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

    public static String readMailPattern(String filePath) throws FileNotFoundException {
        StringBuilder mailFormatBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                mailFormatBuilder.append(line);
                mailFormatBuilder.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mailFormatBuilder.toString();
    }

    public static String readAnniversaryMailPattern() throws FileNotFoundException {
        return readMailPattern(ANNIVERSARY_EMAIL_PATTERN_PATH);
    }

}
