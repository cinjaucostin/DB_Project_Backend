package com.example.backendglobaldirectory.dto;

import lombok.Data;

@Data
public class ForgotPasswordDTO {
    private String email;
    private String password;
    private String confirmPassword;
}
