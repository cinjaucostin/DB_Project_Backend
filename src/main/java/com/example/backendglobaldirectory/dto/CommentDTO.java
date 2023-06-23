package com.example.backendglobaldirectory.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private String text;
    private int userId;
    private int postId;
}
