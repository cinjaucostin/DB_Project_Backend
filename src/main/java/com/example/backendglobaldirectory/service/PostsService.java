package com.example.backendglobaldirectory.service;

import com.example.backendglobaldirectory.entities.Post;
import com.example.backendglobaldirectory.entities.PostType;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.repository.PostsRepository;
import com.example.backendglobaldirectory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostsService {

    @Autowired
    private PostsRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // Se executa in fiecare zi la 12:01 AM(ora Romaniei)
    @Scheduled(cron = "0 1 0 * * *", zone = "Europe/Bucharest")
    public void generateAnniversaryPosts() {
        List<User> users = this.userRepository.findAll();

        for(User user : users) {
            LocalDateTime dateOfEmployment = user.getDateOfEmployment();
            LocalDateTime now = LocalDateTime.now();

            if(dateOfEmployment.getMonth() == now.getMonth()
                    && dateOfEmployment.getDayOfMonth() == now.getDayOfMonth()) {
                int noOfYearsInCompany = now.getYear() - dateOfEmployment.getYear();

                Post post = new Post();
                post.setTimestamp(LocalDateTime.now());
                post.setText("Happy " + noOfYearsInCompany + " years anniversary!");
                post.setType(PostType.ANNIVERSARY);
                post.setUser(user);

                this.postRepository.save(post);
            }

        }

    }

}
