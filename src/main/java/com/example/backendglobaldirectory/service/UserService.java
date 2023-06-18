package com.example.backendglobaldirectory.service;

import com.example.backendglobaldirectory.dto.ForgotPasswordDTO;
import com.example.backendglobaldirectory.dto.MyUserDetails;
import com.example.backendglobaldirectory.dto.RegisterDTO;
import com.example.backendglobaldirectory.dto.ResponseDTO;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.exception.EmailAlreadyUsedException;
import com.example.backendglobaldirectory.exception.ThePasswordsDoNotMatchException;
import com.example.backendglobaldirectory.exception.UserNotFoundException;
import com.example.backendglobaldirectory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
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

    public void save(User newUser) {
        this.userRepository.save(newUser);
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }



    public ResponseEntity<ResponseDTO> changePassword(ForgotPasswordDTO forgotPasswordDTO)
            throws ThePasswordsDoNotMatchException, UserNotFoundException {

        Optional<User> userOptional = this.userRepository.findByEmail(forgotPasswordDTO.getEmail());

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
