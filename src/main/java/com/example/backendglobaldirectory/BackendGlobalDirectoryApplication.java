package com.example.backendglobaldirectory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendGlobalDirectoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendGlobalDirectoryApplication.class, args);
    }

}
