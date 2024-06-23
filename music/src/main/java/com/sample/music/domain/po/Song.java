package com.sample.music.domain.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sample.music.domain.vo.SongWithFiles;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Song extends SongWithFiles {
    private Long id;
    private String title;
    private String artist;
    private String album;
    private String url;
    private String cover;
    private String lyric;
    private String style;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String releaseDate;
}
