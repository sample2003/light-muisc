package com.sample.music.service;

import com.sample.music.domain.po.Song;

import java.util.List;

public interface RelateService {
    void addSongsToPlayList(Long playlist_id, Long id);

    void deleteSongsByPlayList(Long playlist_id, Long id);

    List<Song> selectSongsAllByPlayList(Long id);

    List<Song> selectSongsAllByAlbum(Long id);

}
