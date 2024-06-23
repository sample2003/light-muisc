package com.sample.music.controller;

import com.sample.music.annotation.AdminOnly;
import com.sample.music.domain.result.Result;
import com.sample.music.domain.po.User;
import com.sample.music.domain.vo.EmailVerify;
import com.sample.music.domain.vo.UserRegisterVO;
import com.sample.music.domain.vo.UserUpdateVO;
import com.sample.music.service.UserService;
import com.sample.music.service.impl.EmailService;
import com.sample.music.utils.JwtUtil;
import com.sample.music.utils.Md5Util;
import com.sample.music.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.sample.music.domain.result.HttpStatusCode.OK;

@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final StringRedisTemplate stringRedisTemplate;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    /**
     * 用户注册
     * @param username 用户名
     * @param email 邮箱
     * @param password 密码
     * @return Result
     */
    @PostMapping("register")
    public Result<String> registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password
            ){
        User user = userService.findUserByNameOrEmail(username, email);
        if(user != null){
            return Result.error("用户名或邮箱已存在");
        }
        UserRegisterVO userRegisterVO = new UserRegisterVO();
        userRegisterVO.setUsername(username);
        userRegisterVO.setEmail(email);
        userRegisterVO.setPassword(password);
        userService.registerUser(userRegisterVO);
        return Result.success();
    }


    /**
     * 用户登录
     * @param text 用户名或邮箱
     * @param password 密码
     * @return Result
     */
    @PostMapping("login")
    public Result<String> loginUser(
            @Pattern(regexp=".*") String text,
            @Pattern(regexp=".*") String password
    ){
        User loginUser = userService.findUserByNameOrEmail(text);
        if(loginUser == null){
            return Result.error("用户未注册");
        }

        ValueOperations<String, String> operation = stringRedisTemplate.opsForValue();
        // token_
        String redisKey = "token_" + loginUser.getId();

        String barredToken = operation.get("jwt:blacklist:"+redisKey);
        if(barredToken != null){
            return Result.error("该用户被封禁");
        }
        if(Md5Util.checkPassword(password,loginUser.getPassword())){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = jwtUtil.genToken(claims);

            // 把token存入redis
            operation.set("token:"+redisKey,token,12, TimeUnit.HOURS);
            return Result.success(OK, "登录成功", token);
        }
        return Result.error("登录失败");
    }


    /**
     * 用户登出
     * @return Result
     */
    @GetMapping("logout")
    public Result<?> logoutUser(){
        return Result.success();
    }


    /**
     * 用户查询个人信息
     * @return Result<User>
     */
    @AdminOnly
    @GetMapping("info")
    public Result<User> userInfo(){
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        User user = userService.findUserByNameOrEmail(username);
        return Result.success(OK,"查询成功",user);
    }


    /**
     * 用户更新个人信息
     * @param user 用户信息
     * @return Result
     */
    @PutMapping("updateUser")
    public Result<String> updateUser(@RequestBody UserUpdateVO user){
        user.setUpdateTime(LocalDateTime.now());
        userService.updateUser(user);
        return Result.success("用户信息修改成功");
    }

    @PostMapping("sendVerifyCode")
    public Result<?> sendVerifyCode(@RequestBody EmailVerify emailVerify){
        return userService.sendVerifyCode(emailVerify)
                .map(Result::success)
                .orElseGet(() -> Result.error("发送验证码失败"));
    }


    /**
     * 用户更新密码
     * @param email 邮箱
     * @param code 验证码
     * @param newPassword 新密码
     * @return Result
     */
    @PatchMapping("updateUserPwd")
    public Result<?> updateUserPwd(String email, String code, String newPassword){

        if(email == null){
            return Result.error("邮箱不可为空");
        }else if(code == null){
            return Result.error("验证码为空");
        }else if(newPassword == null){
            return Result.error("密码不可为空");
        }
        if(userService.findUserByNameOrEmail(email) != null){
            if(emailService.validateVerifyCode(email, code)){
                userService.updatePwd(newPassword, email);
                emailService.deleteVerifyCode(email);
                return Result.success("密码重置成功");
            }
        }
        return Result.error("密码重置失败");
    }
}
