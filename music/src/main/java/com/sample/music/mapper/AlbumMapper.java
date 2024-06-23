package com.sample.music.mapper;

import com.sample.music.domain.po.Album;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlbumMapper {
    void insertAlbumByOne(Album album);

    void deleteAlbumById(Long id);

    void updateAlbumById(Album album);

    Album selectAlbumById(Long id);

    List<Album> selectAlbumByAll();
}
