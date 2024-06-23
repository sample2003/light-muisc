package com.sample.music.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sample.music.domain.po.PlayList;
import com.sample.music.mapper.PlayListMapper;
import com.sample.music.domain.po.PageBean;
import com.sample.music.service.PlayListService;

import com.sample.music.utils.ThreadLocalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PlayListServiceImpl implements PlayListService {

    private final PlayListMapper playListMapper;

    @Override
    public void insertPlayListByOne(PlayList playList) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        Long userId = Long.valueOf(id);
        playList.setUserId(userId);
        playList.setCreateTime(LocalDateTime.now());
        playListMapper.insertPlayListByOne(playList);
    }

    @Override
    public void deletePlayListById(Long id) {
        playListMapper.deletePlayListById(id);
    }

    @Override
    public void updatePlayListById(PlayList songList) {
        playListMapper.updatePlayListById(songList);
    }

    @Override
    public List<PlayList> selectPlayListByAll() {
        return playListMapper.selectPlayListByAll();
    }

    @Override
    public PageBean<PlayList> selectPlayListByPage(Integer pageNum, Integer pageSize) {
        // 创建pagebean对象
        PageBean<PlayList> pb = new PageBean<>();
        // 开启分页查询
        PageHelper.startPage(pageNum,pageSize);

        // 调用 mapper 执行分页查询
        Page<PlayList> page = (Page<PlayList>) playListMapper.selectPlayListByPage();

        // 把数据填充到PageBean对象中
        pb.setTotal(page.getTotal());
        pb.setItems(page.getResult());

        return pb;
    }

    @Override
    public PlayList selectPlayListById(Long id) {
        return playListMapper.selectPlayListById(id);
    }

}
