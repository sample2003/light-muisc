package com.sample.music.controller;

import com.sample.music.domain.result.Result;
import com.sample.music.domain.po.Song;
import com.sample.music.service.RelateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relate")
@RequiredArgsConstructor
public class RelateController {

    private final RelateService relateService;

    /**
     * 增加歌曲到歌单
     * @param ids 歌曲id
     * @param playlist_id 歌单id
     * @return Result
     */
    @PostMapping("addSongsToPlayList")
    public Result<String> addSongsToPlayList(@RequestParam Long playlist_id, @RequestBody Long[] ids){
        for (Long id : ids) {
            relateService.addSongsToPlayList(playlist_id, id);
        }
        return Result.success();
    }

    /**
     * 从歌单移除歌曲
     * @param ids 歌曲id
     * @param playlist_id 歌单id
     * @return Result
     */
    @DeleteMapping("deleteSongsByPlayList")
    public Result<String> deleteSongsByPlayList(@RequestParam Long playlist_id, @RequestBody Long[] ids){
        for (Long id : ids){
            relateService.deleteSongsByPlayList(playlist_id, id);
        }
        return Result.success();
    }

    /**
     * 从歌单查询歌曲
     * @param id 歌单id
     * @return Result<List<Song>>
     */
    @GetMapping("selectSongsByPlayList/{id}")
    public Result<List<Song>> selectSongsAllByPlayList(@PathVariable("id") Long id){
        List<Song> song = relateService.selectSongsAllByPlayList(id);
        return Result.success(song);
    }

    /**
     * 查询专辑里的所有歌曲
     * @param id 专辑id
     * @return Result<List<Song>>
     */
    @GetMapping("selectSongsByAlbum/{id}")
    public Result<List<Song>> selectSongsAllByAlbum(@PathVariable("id") Long id){
        List<Song> song = relateService.selectSongsAllByAlbum(id);
        return Result.success(song);
    }

 }
