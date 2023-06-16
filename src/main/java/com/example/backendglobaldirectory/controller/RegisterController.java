package com.example.backendglobaldirectory.controller;

import com.example.backendglobaldirectory.dto.LoginDTO;
import com.example.backendglobaldirectory.dto.RegisterDTO;
import com.example.backendglobaldirectory.dto.ResponseDTO;
import com.example.backendglobaldirectory.exception.EmailAlreadyUsedException;
import com.example.backendglobaldirectory.exception.UserNotFoundException;
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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // /user/register
    // ar trebui sa intoarca User
    // + framework de logging
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

    @PostMapping("/loginSuccess")
    public void test() {
        System.out.println("S-a facut login");
    }

    @GetMapping("/logoutSuccess")
    public void test2() {
        System.out.println("S-a facut logout");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Further handling or processing based on the authentication result

            // Add JSessionId to response headers
            String sessionId = request.getSession().getId();
            response.setHeader("Set-Cookie", "JSESSIONID=" + sessionId + "; HttpOnly; SameSite=Strict");

            return ResponseEntity.ok().build();
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
