package com.sample.music.mapper;

import com.sample.music.domain.query.UserQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<UserQuery> selectUsersByPage(Integer status);

    Long findUserRole(Long userId);

    void BarreOrFreedUser(Long id, Integer status);
}
