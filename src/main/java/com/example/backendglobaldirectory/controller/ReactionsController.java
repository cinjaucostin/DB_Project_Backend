package com.example.backendglobaldirectory.controller;

import com.example.backendglobaldirectory.dto.AddCommentDTO;
import com.example.backendglobaldirectory.dto.CommentDTO;
import com.example.backendglobaldirectory.entities.Comment;
import com.example.backendglobaldirectory.exception.ResourceNotFoundException;
import com.example.backendglobaldirectory.service.ReactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reactions")
@CrossOrigin
public class ReactionsController {

    @Autowired
    private ReactionsService reactionsService;

    @GetMapping("/comments")
    public List<CommentDTO> getComments(@RequestParam(required = false) Integer pid,
                                        @RequestParam(required = false) Integer uid) throws ResourceNotFoundException {
        return this.reactionsService.getCommentsFilteredBy(pid, uid);
    }

    @PostMapping("/comments")
    public ResponseEntity<Comment> addCommentToPostFromUser(
            @RequestBody AddCommentDTO addCommentDTO,
            Principal principal)
            throws ResourceNotFoundException {
        return this.reactionsService.addCommentToPostFromUser(addCommentDTO, principal);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable int id, Principal principal)
            throws ResourceNotFoundException {
        return this.reactionsService.deleteCommentById(id, principal);
    }


}
