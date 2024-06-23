package com.sample.music.service.impl;

import com.sample.music.domain.po.User;
import com.sample.music.domain.query.UserQuery;
import com.sample.music.domain.result.Result;
import com.sample.music.domain.vo.EmailVerify;
import com.sample.music.domain.vo.UserRegisterVO;
import com.sample.music.domain.vo.UserUpdateVO;
import com.sample.music.mapper.UserMapper;
import com.sample.music.service.UserService;
import com.sample.music.utils.Md5Util;
import com.sample.music.utils.ThreadLocalUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    private final EmailService emailService;

    @Override
    public User findUserByNameOrEmail(String username, String email) {
        User user = userMapper.findUserByUserName(username);
        if(user == null){
            user = userMapper.findUserByEmail(email);
        }
        return user;
    }

    @Override
    public User findUserByNameOrEmail(String text) {
        User user = userMapper.findUserByUserName(text);
        if(user == null){
            user = userMapper.findUserByEmail(text);
        }
        return user;
    }

    @Override
    public UserQuery findUserById(Long id) {
        UserQuery user =userMapper.findUserById(id);
        user.setRoleId(userMapper.findUserRole(id));
        return user;
    }

    @Override
    @Transactional
    public void registerUser(UserRegisterVO user) {
        String passwordToMd5 = Md5Util.getMD5String(user.getPassword());
        user.setPassword(passwordToMd5);
        userMapper.registerUser(user);
        Long roleId = 2L;
        Long userId = user.getId();
        System.out.println(roleId+":"+userId);
        userMapper.addUserRole(roleId, userId);
    }

    @Override
    public void updateUser(UserUpdateVO user) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        Long userId = id.longValue();
        userMapper.updateUser(user, userId);
    }
    @Override
    public void updatePwd(String newPassword, String email) {
        String passwordToMd5 = Md5Util.getMD5String(newPassword);
        userMapper.updatePwd(passwordToMd5, email);
    }

    @Override
    public boolean isAdmin(Long userId) {
        Long num = userMapper.isAdmin(userId);
        return num == 1L;
    }

    @Override
    public Optional<EmailVerify> sendVerifyCode(EmailVerify emailVerify) {
        User user = userMapper.findUserByEmail(emailVerify.getEmail());
        if (user == null) {
            return Optional.empty();
        }

        EmailVerify verify = emailService.sendVerificationEmail(user.getEmail());

        return Optional.of(verify);
    }

}
