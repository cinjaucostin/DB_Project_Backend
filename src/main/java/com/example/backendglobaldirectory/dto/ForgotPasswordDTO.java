package com.example.backendglobaldirectory.dto;

import lombok.Data;

@Data
public class ForgotPasswordDTO {
    String email;
    String password;
    String confirmPassword;
}
