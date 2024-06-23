package com.sample.music.mapper;

import com.sample.music.domain.po.Song;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RelateMapper {
    void addSongsToPlayList(Long playlist_id, Long id);

    void deleteSongsByPlayList(Long song_list_id, Long id);

    List<Song> selectSongsAllByPlayList(Long id);

    List<Song> selectSongsAllByAlbum(Long id);

}
