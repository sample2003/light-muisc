package com.sample.music.service.impl;

import com.sample.music.mapper.RelateMapper;
import com.sample.music.domain.po.Album;
import com.sample.music.domain.po.Song;
import com.sample.music.service.RelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelateServiceImpl implements RelateService {

    @Autowired
    private RelateMapper relateMapper;

    @Override
    public void addSongsToPlayList(Long playlist_id, Long id) {
        relateMapper.addSongsToPlayList(playlist_id, id);
    }

    @Override
    public void deleteSongsByPlayList(Long playlist_id, Long id) {
        relateMapper.deleteSongsByPlayList(playlist_id, id);
    }

    @Override
    public List<Song> selectSongsAllByPlayList(Long id) {
        return relateMapper.selectSongsAllByPlayList(id);
    }

    @Override
    public List<Song> selectSongsAllByAlbum(Long id) {
        return relateMapper.selectSongsAllByAlbum(id);
    }
}
