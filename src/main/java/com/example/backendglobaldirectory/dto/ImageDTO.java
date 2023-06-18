package com.example.backendglobaldirectory.dto;

import com.example.backendglobaldirectory.entities.Image;
import com.example.backendglobaldirectory.entities.User;
import lombok.Data;

@Data
public class ImageDTO {
    private String name;
    private String type;
    private String base64Img;

    public static Image toImageEntity(ImageDTO imageDTO) {
        return new Image(
                imageDTO.getName(),
                imageDTO.getType(),
                imageDTO.getBase64Img()
        );
    }

}
