package com.sample.music.domain.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


@Data
public class PlayListQuery {
    private String title;
    private String cover;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-SS")
    private String createTime;
}
