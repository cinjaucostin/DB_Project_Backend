package com.example.backendglobaldirectory.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profile_images")
@NoArgsConstructor
@Getter
@Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String type;

    @Lob
    @Column(name = "image_encoded")
    private String imageEncoded;

    public Image(String name, String type, String imageEncoded) {
        this.name = name;
        this.type = type;
        this.imageEncoded = imageEncoded;
    }

}
