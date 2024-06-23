package com.sample.music.service;

import com.sample.music.domain.po.Song;
import com.sample.music.domain.po.PageBean;

import java.util.List;

public interface SongService {
    PageBean<Song> part(Integer pageNum, Integer pageSize);

    void insertSongWithFiles(Song song);

    void deleteSongById(Long id);

    void updateSongById(Song song);

    List<Song> selectSongsByCondition(String value);

    void insertSongByMore(List<Song> songs);

    Song selectSongById(Long id);
}
