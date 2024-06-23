package com.sample.music.mapper;

import com.sample.music.domain.po.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SongMapper {
    List<Song> part();

    void insertSongWithFiles(Song song);

    void deleteSongById(Long id);

    void updateSongById(Song song);

    List<Song> selectSongsByCondition(String value);

    void insertSongByMore(@Param("songs") List<Song> songs);

    Song selectSongById(Long id);
}
