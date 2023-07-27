package com.example.demo.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileImageDto {
    private int id;
    private MultipartFile file;

    @Pattern(regexp = "^[0-9]+$", message = "User id should contain only numbers!")
    private int userId;
}
