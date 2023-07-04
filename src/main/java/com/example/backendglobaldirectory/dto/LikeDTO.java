package com.example.backendglobaldirectory.dto;

import com.example.backendglobaldirectory.entities.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LikeDTO {
    private int id;
    private int userId;
    private int postId;
    private String userFullName;
    private ImageDTO userProfileImage;

    public static LikeDTO fromEntityToDTO(Like like) {
        User likeUser = like.getUser();
        Post likePost = like.getPost();

        Image profileImage = likeUser.getProfileImage();

        ImageDTO profileImageDTO =
                profileImage == null ?
                null :
                ImageDTO.fromEntity(likeUser.getProfileImage());

        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setId(like.getId());
        likeDTO.setPostId(likePost.getId());
        likeDTO.setUserProfileImage(profileImageDTO);
        likeDTO.setUserId(likeUser.getId());
        likeDTO.setUserFullName(likeUser.getFirstName() +
                " " +
                likeUser.getLastName());

        return likeDTO;
    }

    public static List<LikeDTO> fromEntityListToDTOList(List<Like> likes) {
        List<LikeDTO> likeDTOS = new ArrayList<>();

        likes.forEach(comment -> {
            likeDTOS.add(fromEntityToDTO(comment));
        });

        return likeDTOS;
    }

}
