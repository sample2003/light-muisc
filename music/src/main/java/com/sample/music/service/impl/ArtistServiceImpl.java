package com.sample.music.service.impl;

import com.sample.music.domain.po.Artist;
import com.sample.music.mapper.ArtistMapper;
import com.sample.music.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {
    private final ArtistMapper artistMapper;
    @Override
    public void insertArtistByOne(Artist artist) {
        artistMapper.insertArtistByOne(artist);
    }

    @Override
    public void deleteArtistById(Long id) {
        artistMapper.deleteArtistById(id);

    }

    @Override
    public void updateArtistById(Artist artist) {
        artistMapper.updateArtistById(artist);

    }

    @Override
    public Artist selectArtistById(Long id) {
        return artistMapper.selectArtistById(id);
    }

    @Override
    public List<Artist> selectArtistByAll() {
        return artistMapper.selectArtistByAll();
    }
}
