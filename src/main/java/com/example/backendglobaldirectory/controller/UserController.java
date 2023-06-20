package com.example.backendglobaldirectory.controller;

import com.example.backendglobaldirectory.dto.ResponseDTO;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.exception.UserNotFoundException;
import com.example.backendglobaldirectory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/approve")
    public ResponseEntity<ResponseDTO> approveRegister(@RequestParam int uid)
            throws UserNotFoundException {
        return this.userService.performAccountApproveOrReject(uid, true);
    }

    @PutMapping("/reject")
    public ResponseEntity<ResponseDTO> rejectRegister(@RequestParam int uid)
            throws UserNotFoundException {
        return this.userService.performAccountApproveOrReject(uid, false);
    }

    @PutMapping("/activate")
    public ResponseEntity<ResponseDTO> activateUser(@RequestParam int uid)
            throws UserNotFoundException {
        return this.userService.performAccountStatusSwitch(uid, true);
    }

    @PutMapping("/inactivate")
    public ResponseEntity<ResponseDTO> inactivateUser(@RequestParam int uid)
            throws UserNotFoundException {
        return this.userService.performAccountStatusSwitch(uid, false);
    }

}
