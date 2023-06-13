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

    @Column(length = 10000000)
    private byte[] picBytes;

    public Image(String name, String type, byte[] picBytes) {
        this.name = name;
        this.type = type;
        this.picBytes = picBytes;
    }

}
