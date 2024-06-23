package com.sample.music.controller;

import com.sample.music.annotation.AdminOnly;
import com.sample.music.domain.dto.FileDTO;
import com.sample.music.domain.po.Artist;
import com.sample.music.domain.result.Result;
import com.sample.music.service.ArtistService;
import com.sample.music.service.impl.FileManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artist")
@RequiredArgsConstructor
@AdminOnly
@Transactional
public class ArtistController {
    private final ArtistService artistService;
    private final FileManageService fileManageService;

    @PostMapping("insertArtistByOne")
    public Result<?> insertAlbumByOne(Artist artist, FileDTO image){
        String url = fileManageService.uploadInterface(image.getImage(), "image");
        artist.setAvatar(url);
        artistService.insertArtistByOne(artist);
        return Result.success();
    }

    @DeleteMapping("deleteArtistById/{id}")
    public Result<?> deleteAlbumById(@PathVariable("id") Long id){
        Artist artist = artistService.selectArtistById(id);
        fileManageService.delete(artist.getAvatar());
        artistService.deleteArtistById(id);
        return Result.success();
    }

    @PutMapping("updateArtistById")
    public Result<Artist> updatePlayListById(Artist artist, FileDTO image){
        String url = fileManageService.uploadInterface(image.getImage(), "image");
        artist.setAvatar(url);
        artistService.updateArtistById(artist);
        return Result.success(artist);
    }

    @GetMapping("selectArtistById/{id}")
    public Result<Artist> selectAlbumById(@PathVariable("id") Long id){
        Artist artist = artistService.selectArtistById(id);
        return Result.success(artist);
    }

    @GetMapping("selectArtistByAll")
    public Result<List<Artist>> selectAlbumByAll(){
        List<Artist> artists = artistService.selectArtistByAll();
        return Result.success(artists);
    }
}
