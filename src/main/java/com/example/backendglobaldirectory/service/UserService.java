package com.example.backendglobaldirectory.service;

import com.example.backendglobaldirectory.dto.MyUserDetails;
import com.example.backendglobaldirectory.dto.RegisterDTO;
import com.example.backendglobaldirectory.dto.ResponseDTO;
import com.example.backendglobaldirectory.entities.Image;
import com.example.backendglobaldirectory.entities.Roles;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.exception.EmailAlreadyUsedException;
import com.example.backendglobaldirectory.exception.UserNotFoundException;
import com.example.backendglobaldirectory.repository.UserRepository;
import com.example.backendglobaldirectory.utils.Utils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void initPasswordEncoder() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = this.userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Not found!");
        }

        return new MyUserDetails(optionalUser.get());
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

        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        newUser.setFirstName(registerDTO.getFirstName());
        newUser.setLastName(registerDTO.getLastName());

        newUser.setDateOfEmployment(Utils.convertDateStringToLocalDateTime(
                registerDTO.getDateOfEmployment())
        );

        newUser.setJobTitle(registerDTO.getJobTitle());
        newUser.setTeam(registerDTO.getTeam());
        newUser.setDepartment(registerDTO.getDepartment());
        newUser.setRole(Roles.USER);

        newUser.setApproved(false);

        Image profileImage = new Image(
                registerDTO.getImage().getName(),
                registerDTO.getImage().getType(),
                registerDTO.getImage().getBase64Img()
        );

        newUser.setProfileImage(profileImage);

        this.userRepository.save(newUser);

        return new ResponseEntity<>(
                new ResponseDTO("User registered."),
                HttpStatus.OK
        );
    }

    public ResponseEntity<ResponseDTO> performAccountApprove(int uid)
            throws UserNotFoundException {
        Optional<User> userOptional = this.userRepository.findById(uid);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("No user found with the given uid. Can't perform the approval.");
        }

        userOptional.get().setApproved(true);

        return new ResponseEntity<>(
                new ResponseDTO("User approved succesfully."),
                HttpStatus.OK
        );
    }
}
