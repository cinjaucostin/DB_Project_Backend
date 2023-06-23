package com.example.backendglobaldirectory.controller;

import com.example.backendglobaldirectory.dto.CommentDTO;
import com.example.backendglobaldirectory.entities.Comment;
import com.example.backendglobaldirectory.exception.ResourceNotFoundException;
import com.example.backendglobaldirectory.service.ReactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reactions")
@CrossOrigin
public class ReactionsController {

    @Autowired
    private ReactionsService reactionsService;

    @PostMapping("/comments")
    public ResponseEntity<Comment> addCommentToPostFromUser(@RequestBody CommentDTO commentDTO)
            throws ResourceNotFoundException {
        return this.reactionsService.addCommentToPostFromUser(commentDTO);
    }

}
