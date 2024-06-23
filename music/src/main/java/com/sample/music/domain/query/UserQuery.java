package com.sample.music.domain.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserQuery {
    @JsonIgnore
    private Long id;
    // 值不能为null，并且非空
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}")
    private String username;
    // 转化为json数据时忽略该项
    @JsonIgnore
    private String password;
    // 满足邮箱格式
    @Email
    private String email;
    private String phone;
    private String avatar;
    private Long roleId;
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-SS")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-SS")
    private LocalDateTime updateTime;
}
