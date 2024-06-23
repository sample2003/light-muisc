package com.sample.music.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDTO {
    private MultipartFile image;
    private MultipartFile text;
    private MultipartFile song;
}
