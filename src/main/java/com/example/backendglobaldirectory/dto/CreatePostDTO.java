package com.example.backendglobaldirectory.dto;

import com.example.backendglobaldirectory.entities.PostType;
import lombok.Data;

@Data
public class CreatePostDTO {

    private PostType type;

    private String text;
}
