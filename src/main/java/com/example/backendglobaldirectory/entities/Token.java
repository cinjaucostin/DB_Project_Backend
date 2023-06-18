package com.example.backendglobaldirectory.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    private boolean expired;

    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Token(String token, boolean expired, boolean revoked, User user) {
        this.token = token;
        this.expired = expired;
        this.revoked = revoked;
        this.user = user;
    }

}
