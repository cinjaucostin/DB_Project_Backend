package com.example.backendglobaldirectory.dto;

import com.example.backendglobaldirectory.entities.Comment;
import com.example.backendglobaldirectory.entities.Post;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.utils.Utils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentDTO {
    private int id;
    private int userId;
    private int postId;
    private String userFullName;
    private String text;
    private String timePassed;

    public static CommentDTO fromCommentEntity(Comment comment) {
        User commentUser = comment.getUser();
        Post commentPost = comment.getPost();

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setUserId(commentUser.getId());
        commentDTO.setPostId(commentPost.getId());
        commentDTO.setText(comment.getText());
        commentDTO.setUserFullName(commentUser.getFirstName()
                + " "
                + commentUser.getLastName()
        );
        commentDTO.setTimePassed(Utils.getPeriodOfTimeFrom(comment.getTimestamp()));
        return commentDTO;
    }

    public static List<CommentDTO> fromCommentListToCommentDTOList(List<Comment> comments) {
        List<CommentDTO> commentDTOS = new ArrayList<>();

        comments.forEach(comment -> {
            commentDTOS.add(fromCommentEntity(comment));
        });

        return commentDTOS;
    }

}
