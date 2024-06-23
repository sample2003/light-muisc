package com.sample.music.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.sample.music.domain.po.Song;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SongWithFiles {
    @JsonIgnore
    private MultipartFile lyricsFile;
    @JsonIgnore
    private MultipartFile imageFile;
    @JsonIgnore
    private MultipartFile songFile;
    // 其他Song字段...

    public Song toSong() {
        // 复制字段到Song对象
        return new Song();
    }
}
