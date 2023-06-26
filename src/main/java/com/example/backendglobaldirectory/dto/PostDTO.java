package com.example.backendglobaldirectory.dto;

import com.example.backendglobaldirectory.entities.Comment;
import com.example.backendglobaldirectory.entities.Like;
import com.example.backendglobaldirectory.entities.Post;
import com.example.backendglobaldirectory.entities.User;
import com.example.backendglobaldirectory.utils.Utils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostDTO {
    private int postId;
    private int userId;
    private String userFullName;
    private String timePeriod;
    private String type;

    private ImageDTO postImage;
    private int nrOfLikes;
    private int nrOfComments;
    private List<CommentDTO> comments;

    public static PostDTO fromEntityToDTO(Post post) {
        User postUser = post.getUser();
        List<Comment> comments = post.getComments();
        List<Like> likes = post.getLikes();

        PostDTO postDTO = new PostDTO();
        postDTO.postId = post.getId();
        postDTO.userId = postUser.getId();
        postDTO.userFullName = postUser.getFirstName() + " " + postUser.getLastName();
        postDTO.timePeriod = Utils.getPeriodOfTimeFrom(post.getTimestamp());
        postDTO.type = post.getType().name();
        postDTO.setPostImage(ImageDTO.fromEntity(post.getImage()));
        postDTO.nrOfLikes = likes.size();
        postDTO.nrOfComments = comments.size();
        postDTO.comments = CommentDTO.fromEntityListToDTOList(comments);

        return postDTO;
    }

    public static List<PostDTO> fromEntityListToDTOList(List<Post> posts) {
        List<PostDTO> postDTOS = new ArrayList<>();

        posts.forEach(post -> {
            postDTOS.add(fromEntityToDTO(post));
        });

        return postDTOS;
    }

}
