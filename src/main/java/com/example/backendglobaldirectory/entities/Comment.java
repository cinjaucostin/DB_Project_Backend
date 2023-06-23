package com.example.backendglobaldirectory.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @Schema(description = "A comment can be associated to only one post.")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "A comment can be associated to only one user.")
    private User user;

}
