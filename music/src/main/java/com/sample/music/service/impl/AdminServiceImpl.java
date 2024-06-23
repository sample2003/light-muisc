package com.sample.music.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sample.music.domain.po.PageBean;
import com.sample.music.domain.query.UserQuery;
import com.sample.music.mapper.AdminMapper;
import com.sample.music.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    @Override
    public PageBean<UserQuery> selectUsersByPage(Integer pageNum, Integer pageSize, Integer status) {
        // 创建page bean对象
        PageBean<UserQuery> pb = new PageBean<>();

        // 开启分页查询
        Page<Object> objects = PageHelper.startPage(pageNum, pageSize);
        System.out.println(objects);
        List<UserQuery> users = adminMapper.selectUsersByPage(status);

        // 将List转换为PageInfo，PageInfo包含了分页信息
        PageInfo<UserQuery> pageInfo = new PageInfo<>(users);

        // 把数据填充到PageBean对象中
        pb.setTotal(pageInfo.getTotal());
        pb.setItems(pageInfo.getList());

        // 处理每个UserQuery对象，添加角色ID
        for (UserQuery user : pb.getItems()) {
            Long roleId = adminMapper.findUserRole(user.getId());
            user.setRoleId(roleId);
        }

        return pb;
    }

    @Override
    public void BarreOrFreedUser(Long id, Integer status) {
        adminMapper.BarreOrFreedUser(id, status);
    }
}
