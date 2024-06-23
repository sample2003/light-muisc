package com.sample.music.service;

import com.sample.music.domain.po.PageBean;
import com.sample.music.domain.query.UserQuery;


public interface AdminService {
    PageBean<UserQuery> selectUsersByPage(Integer pageNum, Integer pageSize, Integer status);

    void BarreOrFreedUser(Long id, Integer status);
}
