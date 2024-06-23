package com.sample.music.service;

import com.sample.music.domain.po.Artist;

import java.util.List;

public interface ArtistService {
    void insertArtistByOne(Artist artist);

    void deleteArtistById(Long id);

    void updateArtistById(Artist artist);

    Artist selectArtistById(Long id);

    List<Artist> selectArtistByAll();
}
