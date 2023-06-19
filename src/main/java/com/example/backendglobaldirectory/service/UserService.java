package com.example.backendglobaldirectory.service;

import com.example.backendglobaldirectory.dto.ForgotPasswordDTO;
import com.example.backendglobaldirectory.dto.ResponseDTO;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.exception.ThePasswordsDoNotMatchException;
import com.example.backendglobaldirectory.exception.UserNotFoundException;
import com.example.backendglobaldirectory.repository.UserRepository;
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

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    public ResponseEntity<ResponseDTO> performAccountApproveOrReject(int uid, boolean approved)
            throws UserNotFoundException {

        User user = this.userRepository.findById(uid)
                .orElseThrow(() -> new UserNotFoundException("No user found with the given uid. " +
                        "Can't perform the approval/reject."));

        user.setApproved(approved);
        user.setActive(approved);

        this.userRepository.save(user);

        return new ResponseEntity<>(
                new ResponseDTO("User approved/rejected succesfully."),
                HttpStatus.OK
        );

    }

    public ResponseEntity<ResponseDTO> performAccountApprove(int uid)
            throws UserNotFoundException {


    }

    public ResponseEntity<ResponseDTO> performAccountStatusSwitch(int uid, boolean active)
            throws UserNotFoundException {
        User user = this.userRepository.findById(uid)
                .orElseThrow(() -> new UserNotFoundException("No user found with the given uid. " +
                        "Can't perform the activation/inactivation."));

        user.setActive(active);

        this.userRepository.save(user);

        return new ResponseEntity<>(
                new ResponseDTO("User activated/inactivated succesfully."),
                HttpStatus.OK
        );
    }

    public void save(User newUser) {
        this.userRepository.save(newUser);
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public ResponseEntity<ResponseDTO> changePassword(ForgotPasswordDTO forgotPasswordDTO, String email)
            throws ThePasswordsDoNotMatchException, UserNotFoundException {

        Optional<User> userOptional = this.userRepository.findByEmail(email);

        User user = userOptional.orElseThrow(() -> new UserNotFoundException("No user found with the given email. Can't perform the password change."));

        if (!forgotPasswordDTO.getPassword().equals(forgotPasswordDTO.getConfirmPassword())) {
            throw new ThePasswordsDoNotMatchException("The passwords do not match.");
        }

        user.setPassword(passwordEncoder.encode(forgotPasswordDTO.getPassword()));

        userRepository.save(user);

        return new ResponseEntity<>(
                new ResponseDTO("Password changed."),
                HttpStatus.OK
        );
    }

}
