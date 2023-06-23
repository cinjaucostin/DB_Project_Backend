package com.example.backendglobaldirectory.service;

import com.example.backendglobaldirectory.entities.Post;
import com.example.backendglobaldirectory.entities.PostType;
import com.example.backendglobaldirectory.entities.Roles;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.repository.PostsRepository;
import com.example.backendglobaldirectory.repository.UserRepository;
import com.example.backendglobaldirectory.utils.Utils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostsService {

    @Autowired
    private PostsRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    // Se executa in fiecare zi la 12:01 AM(ora Romaniei)
    @Scheduled(cron = "0 1 0 * * *", zone = "Europe/Bucharest")
    public void generateAnniversaryPosts() throws FileNotFoundException {
        List<User> users = this.userRepository.findAll();

        for(User user : users) {
            LocalDateTime dateOfEmployment = user.getDateOfEmployment();
            LocalDateTime now = LocalDateTime.now();

            if(dateOfEmployment != null) {
                if(dateOfEmployment.getMonth() == now.getMonth()
                        && dateOfEmployment.getDayOfMonth() == now.getDayOfMonth()) {

                    int noOfYearsInCompany = now.getYear() - dateOfEmployment.getYear();

                    Post post = new Post(
                            PostType.ANNIVERSARY_POST,
                            "Happy " + noOfYearsInCompany + " years anniversary!",
                            LocalDateTime.now(),
                            user
                    );

                    this.postRepository.save(post);

                    this.emailSenderService.sendAnniversaryEmailToUser(user, noOfYearsInCompany);
                }
            }

        }

    }
}
