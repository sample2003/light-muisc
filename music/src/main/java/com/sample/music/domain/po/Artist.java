package com.sample.music.domain.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Artist {
    private Long id;
    private String name;
    private String avatar;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String birthday;
}
