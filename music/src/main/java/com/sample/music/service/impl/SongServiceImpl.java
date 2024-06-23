package com.sample.music.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sample.music.mapper.SongMapper;
import com.sample.music.domain.po.Song;
import com.sample.music.domain.po.PageBean;
import com.sample.music.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongMapper songMapper;
    @Override
    public PageBean<Song> part(Integer pageNum, Integer pageSize) {
        PageBean<Song> pb = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Song> songs = songMapper.part();
        System.out.println(songs);
        Page<Song> p = (Page<Song>) songs;
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public void insertSongWithFiles(Song song) {
        songMapper.insertSongWithFiles(song);
    }

    @Override
    public void deleteSongById(Long id) {
        songMapper.deleteSongById(id);
    }

    @Override
    public void updateSongById(Song song) {
        songMapper.updateSongById(song);
    }

    @Override
    public List<Song> selectSongsByCondition(String value) {
        return songMapper.selectSongsByCondition(value);
    }

    @Override
    public void insertSongByMore(List<Song> songs) {
        songMapper.insertSongByMore(songs);
    }

    @Override
    public Song selectSongById(Long id) {
        return songMapper.selectSongById(id);
    }
}
