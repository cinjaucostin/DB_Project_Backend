package com.example.backendglobaldirectory.controller;

import com.example.backendglobaldirectory.dto.PostDTO;
import com.example.backendglobaldirectory.entities.Post;
import com.example.backendglobaldirectory.exception.ResourceNotFoundException;
import com.example.backendglobaldirectory.repository.PostsRepository;
import com.example.backendglobaldirectory.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin
public class PostsController {

    @Autowired
    private PostsService postsService;

    @GetMapping
    public List<PostDTO> getPosts(@RequestParam(required = false) Integer uid)
            throws ResourceNotFoundException {
        return this.postsService.getPostsFilteredBy(uid);
    }

}
