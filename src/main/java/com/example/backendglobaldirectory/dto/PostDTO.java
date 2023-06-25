package com.example.backendglobaldirectory.dto;

import com.example.backendglobaldirectory.entities.PostType;
import com.example.backendglobaldirectory.entities.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {

    private PostType type;

    private String text;
}
