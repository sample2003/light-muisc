package com.sample.music.domain.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlayList {
    private Long id;
    private Long userId;
    private String title;
    private String cover;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-SS")
    private LocalDateTime createTime;
}
