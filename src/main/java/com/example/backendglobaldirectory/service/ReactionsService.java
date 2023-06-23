package com.example.backendglobaldirectory.service;

import com.example.backendglobaldirectory.dto.CommentDTO;
import com.example.backendglobaldirectory.entities.Comment;
import com.example.backendglobaldirectory.entities.Post;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.exception.ResourceNotFoundException;
import com.example.backendglobaldirectory.exception.UserNotFoundException;
import com.example.backendglobaldirectory.repository.CommentRepository;
import com.example.backendglobaldirectory.repository.PostsRepository;
import com.example.backendglobaldirectory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReactionsService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostsRepository postsRepository;

    public ResponseEntity<Comment> addCommentToPostFromUser(CommentDTO commentDTO)
            throws ResourceNotFoundException {
        User user = this.userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Post post = this.postsRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found!"));

        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setTimestamp(LocalDateTime.now());
        comment.setPost(post);
        comment.setUser(user);

        Comment commentSaved = this.commentRepository.save(comment);

        return new ResponseEntity<>(commentSaved, HttpStatus.OK);
    }

}
