package com.sample.music.service.impl;

import com.sample.music.domain.po.Album;
import com.sample.music.mapper.AlbumMapper;
import com.sample.music.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumMapper albumMapper;
    @Override
    public void insertAlbumByOne(Album album) {
        albumMapper.insertAlbumByOne(album);
    }

    @Override
    public void deleteAlbumById(Long id) {
        albumMapper.deleteAlbumById(id);
    }

    @Override
    public void updateAlbumById(Album album) {
        albumMapper.updateAlbumById(album);
    }

    @Override
    public Album selectAlbumById(Long id) {
        return albumMapper.selectAlbumById(id);
    }

    @Override
    public List<Album> selectAlbumByAll() {
        return albumMapper.selectAlbumByAll();
    }
}
