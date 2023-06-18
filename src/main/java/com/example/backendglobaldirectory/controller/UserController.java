package com.example.backendglobaldirectory.controller;

import com.example.backendglobaldirectory.dto.ResponseDTO;
import com.example.backendglobaldirectory.exception.UserNotFoundException;
import com.example.backendglobaldirectory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/approve")
    public ResponseEntity<ResponseDTO> approveRegister(@RequestParam int uid)
            throws UserNotFoundException {
        return this.userService.performAccountApprove(uid);
    }

}
