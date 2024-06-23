package com.sample.music.service;

import com.sample.music.domain.po.User;
import com.sample.music.domain.query.UserQuery;
import com.sample.music.domain.result.Result;
import com.sample.music.domain.vo.EmailVerify;
import com.sample.music.domain.vo.UserRegisterVO;
import com.sample.music.domain.vo.UserUpdateVO;

import java.util.Optional;

public interface UserService {
    User findUserByNameOrEmail(String username, String email);
    User findUserByNameOrEmail(String text);
    UserQuery findUserById(Long id);
    void registerUser(UserRegisterVO user);
    void updateUser(UserUpdateVO user);
    void updatePwd(String newPassword, String email);
    boolean isAdmin(Long userId);
    Optional<EmailVerify> sendVerifyCode(EmailVerify emailVerify);
}
