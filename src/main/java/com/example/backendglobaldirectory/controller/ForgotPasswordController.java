package com.example.backendglobaldirectory.controller;

import com.example.backendglobaldirectory.dto.ForgotPasswordDTO;
import com.example.backendglobaldirectory.dto.RegisterDTO;
import com.example.backendglobaldirectory.dto.ResponseDTO;
import com.example.backendglobaldirectory.dto.SendEmailDTO;
import com.example.backendglobaldirectory.exception.EmailAlreadyUsedException;
import com.example.backendglobaldirectory.exception.ThePasswordsDoNotMatchException;
import com.example.backendglobaldirectory.exception.UserNotFoundException;
import com.example.backendglobaldirectory.service.EmailSenderService;
import com.example.backendglobaldirectory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ForgotPasswordController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailSenderService emailSenderService;

    @PatchMapping("/reset")
    public ResponseEntity<ResponseDTO> register(@RequestBody ForgotPasswordDTO forgotPasswordDTO)
            throws ThePasswordsDoNotMatchException, UserNotFoundException {
        return this.userService.changePassword(forgotPasswordDTO);
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<String> register(@RequestBody SendEmailDTO sendEmailDTO)
    {
        return new ResponseEntity<>(this.emailSenderService.createEmail(sendEmailDTO.getEmail()), HttpStatus.OK);
    }

}
