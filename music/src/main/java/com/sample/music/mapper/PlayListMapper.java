package com.sample.music.mapper;

import com.sample.music.domain.po.PlayList;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlayListMapper{
    void insertPlayListByOne(PlayList songList);
    void deletePlayListById(Long id);
    void updatePlayListById(PlayList songList);
    List<PlayList> selectPlayListByAll();
    PlayList selectPlayListById(Long id);
    List<PlayList> selectPlayListByPage();
}
