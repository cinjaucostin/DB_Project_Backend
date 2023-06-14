package com.example.backendglobaldirectory.controller;

import com.example.backendglobaldirectory.dto.ForgotPasswordDTO;
import com.example.backendglobaldirectory.dto.ResponseDTO;
import com.example.backendglobaldirectory.exception.ThePasswordsDoNotMatchException;
import com.example.backendglobaldirectory.exception.UserNotFoundException;
import com.example.backendglobaldirectory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgotPasswordController {
    @Autowired
    private UserService userService;

    @PatchMapping("/reset")
    public ResponseEntity<ResponseDTO> register(@RequestBody ForgotPasswordDTO forgotPasswordDTO)
            throws ThePasswordsDoNotMatchException, UserNotFoundException {
        return this.userService.changePassword(forgotPasswordDTO);
    }
}
