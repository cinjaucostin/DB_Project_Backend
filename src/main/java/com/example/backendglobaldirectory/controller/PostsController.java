package com.example.backendglobaldirectory.controller;

import com.example.backendglobaldirectory.entities.Post;
import com.example.backendglobaldirectory.repository.PostsRepository;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostsController {

    @Autowired
    private PostsRepository postsRepository;

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return this.postsRepository.findAll();
    }

    @PostMapping("/loginSuccess")
    public void test() {
        System.out.println("S-a facut login");
    }

    @GetMapping("/logoutSuccess")
    public void test2() {
        System.out.println("S-a facut logout");
    }

}
