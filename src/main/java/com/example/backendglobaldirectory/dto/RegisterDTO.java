package com.example.backendglobaldirectory.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterDTO {
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String dateOfEmployment;

    private String jobTitle;

    private String team;

    private String department;

    private ImageDTO image;
}
