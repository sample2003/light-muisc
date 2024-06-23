package com.sample.music.service;

import com.sample.music.domain.po.PageBean;
import com.sample.music.domain.po.PlayList;

import java.util.List;

public interface PlayListService {
    void insertPlayListByOne(PlayList playList);

    void deletePlayListById(Long id);

    void updatePlayListById(PlayList playList);

    List<PlayList> selectPlayListByAll();

    PlayList selectPlayListById(Long id);

    PageBean<PlayList> selectPlayListByPage(Integer pageNum, Integer pageSize);
}
