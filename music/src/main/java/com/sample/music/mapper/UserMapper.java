package com.sample.music.mapper;

import com.sample.music.domain.po.User;
import com.sample.music.domain.query.UserQuery;
import com.sample.music.domain.vo.UserRegisterVO;
import com.sample.music.domain.vo.UserUpdateVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface UserMapper {
    User findUserByUserName(String username);

    User findUserByEmail(String email);
    Long registerUser(UserRegisterVO user);
    void addUserRole(Long roleId, Long userId);
    Long findUserRole(Long userId);
    void updateUser(UserUpdateVO user, Long userId);

    Long isAdmin(Long userId);

    void updatePwd(String newPassword, String email);

    UserQuery findUserById(Long id);
}
