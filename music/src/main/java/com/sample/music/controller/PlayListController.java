package com.sample.music.controller;

import com.sample.music.domain.po.PageBean;
import com.sample.music.domain.po.PlayList;
import com.sample.music.domain.result.Result;
import com.sample.music.service.PlayListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
public class PlayListController {

    private final PlayListService playListService;

    /**
     * 用户创建歌单
     * @param playList 歌单
     * @return Result
     */
    @PostMapping("insertPlayListByOne")
    public Result<String> insertPlayListByOne(@RequestBody PlayList playList){
        playListService.insertPlayListByOne(playList);
        return Result.success();
    }

    /**
     * 删除歌单
     * @param id 歌单id
     * @return Result
     */
    @DeleteMapping("deletePlayListById/{id}")
    public Result<String> deletePlayListById(@PathVariable("id") Long id){
        playListService.deletePlayListById(id);
        return Result.success();
    }

    /**
     * 修改歌单
     * @param playList 歌单
     * @return Result
     */
    @PutMapping("updatePlayListById")
    public Result<PlayList> updatePlayListById(@RequestBody PlayList playList){
        playListService.updatePlayListById(playList);
        return Result.success(playList);
    }

    /**
     * 查询所有歌单
     * @return Result<List<PlayList>>
     */
    @GetMapping("selectPlayListByAll")
    public Result<List<PlayList>> selectPlayListByAll(){
        List<PlayList> playLists = playListService.selectPlayListByAll();
        return Result.success(playLists);
    }

    /**
     * 分页查询歌单
     * @return Result<PageBean<PlayList>>
     */
    @GetMapping("selectPlayListByPage")
    public Result<PageBean<PlayList>> selectPlayListByPage(
            Integer pageNum,Integer pageSize
    ){
        PageBean<PlayList> pb = playListService.selectPlayListByPage(pageNum,pageSize);
        return Result.success(pb);
    }

    /**
     * 查询一个歌单
     * @param id 歌单id
     * @return Result<PlayList>
     */
    @GetMapping("selectPlayListById/{id}")
    public Result<PlayList> selectPlayListById(@PathVariable("id") Long id){
        PlayList playList = playListService.selectPlayListById(id);
        return Result.success(playList);
    }
 }
