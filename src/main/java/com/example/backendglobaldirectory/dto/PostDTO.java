package com.example.backendglobaldirectory.dto;

import com.example.backendglobaldirectory.entities.*;
import com.example.backendglobaldirectory.utils.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class PostDTO implements Comparable<PostDTO> {

    private int postId;

    private int userId;

    private String userFullName;

    private String text;

    private ImageDTO userProfileImage;

    @JsonIgnore
    private long intervalInMinutes;

    private boolean liked;

    private String timePeriod;

    private String type;

    private ImageDTO postImage;

    private int nrOfLikes;

    private int nrOfComments;

    private List<CommentDTO> comments;

    public static PostDTO fromEntityToDTO(Post post) {
        User postUser = post.getUser();
        Image userProfileImage = postUser.getProfileImage();
        List<Comment> comments = post.getComments();
        List<Like> likes = post.getLikes();

        PostDTO postDTO = new PostDTO();
        postDTO.postId = post.getId();
        postDTO.userId = postUser.getId();
        postDTO.userFullName = postUser.getFirstName() + " " + postUser.getLastName();
        postDTO.text = post.getText();
        postDTO.intervalInMinutes = Utils.getPeriodOfTimeInMinutesFrom(post.getTimestamp());
        postDTO.userProfileImage = userProfileImage == null ? null : ImageDTO.fromEntity(userProfileImage);
        postDTO.timePeriod = Utils.getPeriodOfTimeAsString(postDTO.intervalInMinutes);
        postDTO.type = post.getType().name();
        postDTO.postImage = post.getImage() == null ? null : ImageDTO.fromEntity(post.getImage());
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

        Collections.sort(postDTOS);

        return postDTOS;
    }

    @Override
    public int compareTo(PostDTO o) {
        return Long.compare(this.intervalInMinutes, o.intervalInMinutes);
    }

}
