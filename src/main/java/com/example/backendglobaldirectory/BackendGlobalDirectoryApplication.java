package com.example.backendglobaldirectory;

import com.example.backendglobaldirectory.entities.Roles;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class BackendGlobalDirectoryApplication {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            User adminUser = new User();
            adminUser.setApproved(true);
            adminUser.setActive(true);
            adminUser.setEmail("admin");
            adminUser.setPassword(bCryptPasswordEncoder.encode("admin"));
            adminUser.setRole(Roles.ADMIN);

            userRepository.save(adminUser);

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendGlobalDirectoryApplication.class, args);
    }

}
