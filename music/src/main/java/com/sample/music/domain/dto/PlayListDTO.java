package com.sample.music.domain.dto;

import lombok.Data;

@Data
public class PlayListDTO {
    private Long id;
    private String title;
    private String cover;
    private String description;
}
