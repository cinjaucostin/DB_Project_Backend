package com.example.backendglobaldirectory.controller;

import com.example.backendglobaldirectory.dto.LoginDTO;
import com.example.backendglobaldirectory.dto.LoginResponse;
import com.example.backendglobaldirectory.dto.RegisterDTO;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.exception.EmailAlreadyUsedException;
import com.example.backendglobaldirectory.service.AuthenticationService;
import com.example.backendglobaldirectory.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterDTO registerDTO)
            throws EmailAlreadyUsedException {
        return this.authenticationService.performRegister(registerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginRequest,
                                               HttpServletRequest request,
                                               HttpServletResponse response) {
        return this.authenticationService.performLogin(loginRequest, request, response);
    }

}
