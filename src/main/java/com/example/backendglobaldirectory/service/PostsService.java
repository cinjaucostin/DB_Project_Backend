package com.example.backendglobaldirectory.service;

import com.example.backendglobaldirectory.dto.CreatePostDTO;
import com.example.backendglobaldirectory.dto.CommentDTO;
import com.example.backendglobaldirectory.dto.PostDTO;
import com.example.backendglobaldirectory.entities.Post;
import com.example.backendglobaldirectory.entities.PostType;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.exception.ResourceNotFoundException;
import com.example.backendglobaldirectory.repository.PostsRepository;
import com.example.backendglobaldirectory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostsService {

    @Autowired
    private PostsRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    public void createPost(String email, CreatePostDTO createPostDTO) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent())   {
            Post post = new Post(createPostDTO.getType(), createPostDTO.getText(), LocalDateTime.now(), user.get());
            postRepository.save(post);
        }
    }

    // Se executa in fiecare zi la 12:01 AM(ora Romaniei)
    @Scheduled(cron = "0 1 0 * * *", zone = "Europe/Bucharest")
    public void generateAnniversaryPosts() throws FileNotFoundException {
        List<User> users = this.userRepository.findAll();

        for(User user : users) {
            LocalDateTime dateOfEmployment = user.getDateOfEmployment();
            LocalDateTime now = LocalDateTime.now();

            if(dateOfEmployment != null && user.isActive()) {
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

//                    this.emailSenderService.sendAnniversaryEmailToUser(user, noOfYearsInCompany);
                }
            }

        }

    }

    public List<PostDTO> getPostsFilteredBy(Integer uid)
            throws ResourceNotFoundException {
        if(uid != null) {
            return getPostsByUserId(uid);
        }

        return getAllPosts();
    }

    private List<PostDTO> getAllPosts() {
        return PostDTO.fromEntityListToDTOList(
                this.postRepository.findAll()
        );
    }

    private List<PostDTO> getPostsByUserId(Integer uid)
            throws ResourceNotFoundException {
        User user = this.userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        return PostDTO.fromEntityListToDTOList(
                user.getPosts()
        );
    }

}
