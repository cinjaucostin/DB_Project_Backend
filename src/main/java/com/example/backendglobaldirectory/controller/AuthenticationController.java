package com.example.backendglobaldirectory.controller;

import com.example.backendglobaldirectory.dto.LoginDTO;
import com.example.backendglobaldirectory.dto.RegisterDTO;
import com.example.backendglobaldirectory.dto.ResponseDTO;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.exception.EmailAlreadyUsedException;
import com.example.backendglobaldirectory.exception.UserNotFoundException;
import com.example.backendglobaldirectory.service.AuthenticationService;
import com.example.backendglobaldirectory.service.JwtService;
import com.example.backendglobaldirectory.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    // /user/register
    // ar trebui sa intoarca User
    // + framework de logging
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterDTO registerDTO)
            throws EmailAlreadyUsedException {
        return this.authenticationService.performRegister(registerDTO);
    }

    @PutMapping("/approve")
    public ResponseEntity<ResponseDTO> approveRegister(@RequestParam int uid)
            throws UserNotFoundException {
        return this.userService.performAccountApprove(uid);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginRequest,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        return this.authenticationService.performLogin(loginRequest, request, response);
    }

}
