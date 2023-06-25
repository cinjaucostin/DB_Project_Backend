package com.example.backendglobaldirectory.service;

import com.example.backendglobaldirectory.dto.AddCommentDTO;
import com.example.backendglobaldirectory.dto.CommentDTO;
import com.example.backendglobaldirectory.dto.ResponseDTO;
import com.example.backendglobaldirectory.entities.Comment;
import com.example.backendglobaldirectory.entities.Post;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.exception.ResourceNotFoundException;
import com.example.backendglobaldirectory.repository.CommentRepository;
import com.example.backendglobaldirectory.repository.PostsRepository;
import com.example.backendglobaldirectory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReactionsService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostsRepository postsRepository;

    public ResponseEntity<Comment> addCommentToPostFromUser(AddCommentDTO addCommentDTO, Principal principal)
            throws ResourceNotFoundException {
        User user = this.userRepository.findById(addCommentDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        if(!user.getEmail().equals(principal.getName())) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        Post post = this.postsRepository.findById(addCommentDTO.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found!"));

        Comment comment = new Comment();
        comment.setText(addCommentDTO.getText());
        comment.setTimestamp(LocalDateTime.now());
        comment.setPost(post);
        comment.setUser(user);

        Comment commentSaved = this.commentRepository.save(comment);

        return new ResponseEntity<>(commentSaved, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteCommentById(int commentId, Principal principal)
            throws ResourceNotFoundException {
        Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found!"));

        if(!comment.getUser().getEmail().equals(principal.getName()) &&
            !comment.getPost().getUser().getEmail().equals(principal.getName())) {
            return new ResponseEntity<>(
                    new ResponseDTO("Can't delete a comment if this or the post is not yours!"),
                    HttpStatus.FORBIDDEN
            );
        }

        this.commentRepository.delete(comment);

        return new ResponseEntity<>(
                new ResponseDTO("Comment removed succesfully"),
                HttpStatus.OK
        );
    }


    public List<CommentDTO> getCommentsFilteredBy(Integer pid, Integer uid)
            throws ResourceNotFoundException {
        if(pid != null) {
            return getCommentsByPostId(pid);
        }
        if(uid != null) {
            return getCommentsByUserId(uid);
        }
        return getAllComments();
    }

    public List<CommentDTO> getAllComments() {
        return CommentDTO.fromCommentListToCommentDTOList(
                this.commentRepository.findAll()
        );
    }

    public List<CommentDTO> getCommentsByPostId(int pid)
            throws ResourceNotFoundException {
        Post post = this.postsRepository.findById(pid)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found!"));

        return CommentDTO.fromCommentListToCommentDTOList(
                post.getComments()
        );

    }

    public List<CommentDTO> getCommentsByUserId(Integer uid)
            throws ResourceNotFoundException {
        User user = this.userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        return CommentDTO.fromCommentListToCommentDTOList(
                user.getComments()
        );
    }

}
