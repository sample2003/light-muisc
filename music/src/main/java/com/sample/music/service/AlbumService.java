package com.sample.music.service;

import com.sample.music.domain.po.Album;

import java.util.List;

public interface AlbumService {
    void insertAlbumByOne(Album album);

    void deleteAlbumById(Long id);

    void updateAlbumById(Album album);

    Album selectAlbumById(Long id);

    List<Album> selectAlbumByAll();
}
