package com.sample.music.controller;

import com.sample.music.annotation.AdminOnly;
import com.sample.music.domain.po.Album;
import com.sample.music.domain.result.Result;
import com.sample.music.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/album")
@RequiredArgsConstructor
@AdminOnly
public class AlbumController {
    private final AlbumService albumService;

    @PostMapping("insertAlbumByOne")
    public Result<String> insertAlbumByOne(@RequestBody Album album){
        albumService.insertAlbumByOne(album);
        return Result.success();
    }

    @DeleteMapping("deleteAlbumById/{id}")
    public Result<String> deleteAlbumById(@PathVariable("id") Long id){
        albumService.deleteAlbumById(id);
        return Result.success();
    }

    @PutMapping("updateAlbumById")
    public Result<Album> updatePlayListById(@RequestBody Album album){
        albumService.updateAlbumById(album);
        return Result.success(album);
    }

    @GetMapping("selectAlbumById/{id}")
    public Result<Album> selectAlbumById(@PathVariable("id") Long id){
        Album album = albumService.selectAlbumById(id);
        return Result.success(album);
    }

    @GetMapping("selectAlbumByAll")
    public Result<List<Album>> selectAlbumByAll(){
        List<Album> albums = albumService.selectAlbumByAll();
        return Result.success(albums);
    }
}
