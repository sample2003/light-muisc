package com.sample.music.controller;

import com.sample.music.annotation.AdminOnly;
import com.sample.music.annotation.Status;
import com.sample.music.domain.po.PageBean;
import com.sample.music.domain.query.UserQuery;
import com.sample.music.domain.result.Result;
import com.sample.music.service.AdminService;
import com.sample.music.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@AdminOnly
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    private final StringRedisTemplate template;
    private final UserService userService;

    /**
     * 分页显示用户信息
     * @param pageNum 分页页数
     * @param pageSize 每页大小
     * @param status 用户状态
     * @return Result<PageBean<UserQuery>>
     */
    @GetMapping("selectUsersByPage")
    public Result<PageBean<UserQuery>> selectUsersByPage(
            Integer pageNum, Integer pageSize,
            @RequestParam(required = false) Integer status
    ){
        PageBean<UserQuery> pb = adminService.selectUsersByPage(pageNum, pageSize, status);
        return Result.success(pb);
    }

    /**
     * 封禁或释放用户
     * @param ids 用户组id
     * @param status 用户状态
     * @return Result
     */
    @PatchMapping("BarreOrFreedUser")
    public Result<String> BarreOrFreedUser(Long[] ids, @Status Integer status) {
        boolean operationResult = true; // 假设所有操作都成功，如果有失败则设置为false
        List<Long> processedIds = new ArrayList<>(); // 用于存储已处理的用户ID

        for (Long id : ids) {
            if (userService.findUserById(id) != null) {
                String redisKey = "jwt:blacklist:token_" + id;
                boolean isBlacklisted = Boolean.TRUE.equals(template.hasKey(redisKey));

                if (status == 1) { // 解除黑名单
                    if (isBlacklisted) {
                        template.delete(redisKey);
                        adminService.BarreOrFreedUser(id, status);
                        processedIds.add(id);
                    }
                } else { // 添加到黑名单
                    if (!isBlacklisted) {
                        long expire = TimeUnit.HOURS.toMillis(7);
                        template.opsForValue().set(redisKey, "ban", expire, TimeUnit.MILLISECONDS);
                        adminService.BarreOrFreedUser(id, status);
                        processedIds.add(id);
                    }
                }
            } else {
                operationResult = false; // 如果找不到用户，认为操作失败
            }
        }

        if (operationResult && !processedIds.isEmpty()) {
            return Result.success("操作成功");
        } else if (processedIds.isEmpty()) {
            return Result.error("没有用户被处理");
        } else {
            return Result.error("部分用户"+processedIds+"操作成功，部分失败");
        }
    }
}
