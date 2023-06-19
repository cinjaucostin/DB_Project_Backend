package com.example.backendglobaldirectory.controller;

import com.example.backendglobaldirectory.entities.Post;
import com.example.backendglobaldirectory.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postsRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return this.postsRepository.findAll();
    }

}
