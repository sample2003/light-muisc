package com.sample.music.controller;

import com.sample.music.annotation.AdminOnly;
import com.sample.music.domain.po.Song;
import com.sample.music.domain.po.PageBean;
import com.sample.music.domain.result.Result;
import com.sample.music.service.SongService;
import com.sample.music.service.impl.FileManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/song")
@AdminOnly
@Transactional
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;

    private final FileManageService fileManageService;

    /**
     * 分页显示歌曲
     * @param pageNum 分页页数
     * @param pageSize 每页大小
     * @return Result<PageBean<Song>>
     */
    @GetMapping("part")
    public Result<PageBean<Song>> part(
            Integer pageNum, Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String State
    ){
        PageBean<Song> pb = songService.part(pageNum,pageSize);
        return Result.success(pb);
    }

    /**
     * 新增歌曲
     * @param songWithFiles 歌曲与文件
     * @return Result<Song>
     */
    @PostMapping("insertSongWithFiles")
    public Result<Song> insertSongWithFiles(Song songWithFiles){
        try {
            // 上传歌词文件到OSS
            String lyricUrl = fileManageService.uploadInterface(songWithFiles.getLyricsFile(), "lyric");
            // 上传图片文件到OSS
            String coverUrl = fileManageService.uploadInterface(songWithFiles.getImageFile(), "image");
            // 上传歌曲文件到OSS
            String songUrl = fileManageService.uploadInterface(songWithFiles.getSongFile(),"song");

            if(!lyricUrl.equals("error") && !coverUrl.equals("error") && !songUrl.equals("error")){
                // 设置URL到Song对象
                songWithFiles.setLyric(lyricUrl);
                songWithFiles.setCover(coverUrl);
                songWithFiles.setUrl(songUrl);

                // 保存到数据库
                songService.insertSongWithFiles(songWithFiles);

                return Result.success(songWithFiles);
            }
            return Result.error("文件上传失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    /**
     * 新增多首歌曲
     * @param songs 多首歌曲
     * @return Result
     */
    @PostMapping("insertSongByMore")
    public Result<?> insertSongByMore(@RequestBody List<Song> songs){
        songService.insertSongByMore(songs);
        return Result.success();
    }

    /**
     * 删除歌曲
     * @param id 歌曲id
     * @return Result
     */
    @DeleteMapping("deleteSongById/{id}")
    public Result<?> deleteSongById(@PathVariable("id") Long id){
        Song song = songService.selectSongById(id);
        fileManageService.delete(song.getLyric());
        fileManageService.delete(song.getCover());
        fileManageService.delete(song.getUrl());
        songService.deleteSongById(id);
        return Result.success();
    }

    /**
     * 更新歌曲
     * @param songWithFiles 歌曲与文件
     * @return Result<Song>
     */
    @PutMapping("updateSongById")
    public Result<Song> updateSongById(Song songWithFiles){
        // 上传歌词文件到OSS
        String lyricUrl = fileManageService.uploadInterface(songWithFiles.getLyricsFile(), "lyric");
        // 上传图片文件到OSS
        String coverUrl = fileManageService.uploadInterface(songWithFiles.getImageFile(), "image");
        // 上传歌曲文件到OSS
        String songUrl = fileManageService.uploadInterface(songWithFiles.getSongFile(),"song");

        songWithFiles.setLyric(lyricUrl);
        songWithFiles.setCover(coverUrl);
        songWithFiles.setUrl(songUrl);

        songService.updateSongById(songWithFiles);
        return Result.success(songWithFiles);
    }

    /**
     * 条件查询歌曲
     * @param value 条件
     * @return Result<List<Song>>
     */
    @GetMapping("selectSongsByCondition")
    public Result<List<Song>> selectSongsByCondition(@RequestParam String value){
        List<Song> songs = songService.selectSongsByCondition(value);
        return Result.success(songs);
    }

    /**
     * 查询一首歌曲
     * @param id 歌曲id
     * @return null
     */
    @GetMapping("selectSongById/{id}")
    public Result<Song> selectById(@PathVariable("id") Long id){
        Song song = songService.selectSongById(id);
        if(song == null){
            return Result.error("查询失败");
        }
        return Result.success(song);
    }
}
