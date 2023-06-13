package com.example.backendglobaldirectory.service;

import com.example.backendglobaldirectory.dto.MyUserDetails;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.repository.UserRepository;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.getUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Not found!");
        }
        return new MyUserDetails(user);
    }
}
