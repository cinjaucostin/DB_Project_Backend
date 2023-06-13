package com.example.backendglobaldirectory.controller;

import com.example.backendglobaldirectory.dto.RegisterDTO;
import com.example.backendglobaldirectory.dto.ResponseDTO;
import com.example.backendglobaldirectory.exception.EmailAlreadyUsedException;
import com.example.backendglobaldirectory.exception.UserNotFoundException;
import com.example.backendglobaldirectory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterDTO registerDTO)
            throws EmailAlreadyUsedException {
        return this.userService.performRegister(registerDTO);
    }

    @PutMapping("/approve")
    public ResponseEntity<ResponseDTO> approveRegister(@RequestParam int uid)
            throws UserNotFoundException {
        return this.userService.performAccountApprove(uid);
    }


}
