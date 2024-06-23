package com.sample.music.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegisterVO {
    private Long id;
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}")
    private String username;
    // 转化为json数据时忽略该项
    @JsonIgnore
    private String password;
    @Email
    private String email;
}
