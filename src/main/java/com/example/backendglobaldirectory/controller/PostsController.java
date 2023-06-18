package com.example.backendglobaldirectory.controller;

import com.example.backendglobaldirectory.entities.Post;
import com.example.backendglobaldirectory.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostsController {

    @Autowired
    private PostsRepository postsRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return this.postsRepository.findAll();
    }

}