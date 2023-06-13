package com.example.backendglobaldirectory.service;

import com.example.backendglobaldirectory.dto.RegisterDTO;
import com.example.backendglobaldirectory.dto.ResponseDTO;
import com.example.backendglobaldirectory.entities.Roles;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.exception.EmailAlreadyUsedException;
import com.example.backendglobaldirectory.exception.UserNotFoundException;
import com.example.backendglobaldirectory.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void initPasswordEncoder() {
//        passwordEncoder = new BCryptPasswordEncoder();
    }

    public ResponseEntity<ResponseDTO> performRegister(RegisterDTO registerDTO)
            throws EmailAlreadyUsedException {
        Optional<User> userByEmailOptional = this.userRepository
                .findByEmail(registerDTO.getEmail());

        if (userByEmailOptional.isPresent()) {
            throw new EmailAlreadyUsedException("Email already used.");
        }

        User newUser = new User();
        newUser.setEmail(registerDTO.getEmail());

//        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        newUser.setFirstName(registerDTO.getFirstName());
        newUser.setLastName(registerDTO.getLastName());
        newUser.setDateOfEmployment(registerDTO.getDateOfEmployment());
        newUser.setJobTitle(registerDTO.getJobTitle());
        newUser.setTeam(registerDTO.getTeam());
        newUser.setDepartment(registerDTO.getDepartment());
        newUser.setRole(Roles.USER);

        newUser.setApproved(false);

        this.userRepository.save(newUser);

        return new ResponseEntity<>(
                new ResponseDTO("User registered."),
                HttpStatus.OK
        );
    }

    public ResponseEntity<ResponseDTO> performAccountApprove(int uid)
            throws UserNotFoundException {
        Optional<User> userOptional = this.userRepository.findById(uid);

        if(userOptional.isEmpty()) {
            throw new UserNotFoundException("No user found with the given uid. Can't perform the approval.");
        }

        userOptional.get().setApproved(true);

        return new ResponseEntity<>(
                new ResponseDTO("User approved succesfully."),
                HttpStatus.OK
        );
    }

}
