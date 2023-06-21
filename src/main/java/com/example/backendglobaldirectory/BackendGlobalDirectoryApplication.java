package com.example.backendglobaldirectory;

import com.example.backendglobaldirectory.entities.Roles;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@SpringBootApplication
@EnableScheduling
public class BackendGlobalDirectoryApplication {

    @Bean
    CommandLineRunner commandLineRunner(
            @Value("${user.admin.email}") String adminEmail,
            @Value("${user.admin.password}") String adminPassword,
            UserRepository userRepository) {
        return args -> {
            if(userRepository.findByEmail(adminEmail).isPresent()) {
                return;
            }

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            User adminUser = new User();
            adminUser.setApproved(true);
            adminUser.setActive(true);
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(bCryptPasswordEncoder.encode(adminPassword));
            adminUser.setRole(Roles.ADMIN);

            userRepository.save(adminUser);

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendGlobalDirectoryApplication.class, args);
    }

}
