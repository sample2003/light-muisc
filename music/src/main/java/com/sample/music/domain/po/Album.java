package com.sample.music.domain.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Album {
    private Long id;
    private String title;
    private String artist;
    private String cover;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String releaseDate;
}
