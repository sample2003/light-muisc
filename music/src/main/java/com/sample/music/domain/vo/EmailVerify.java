package com.sample.music.domain.vo;

import lombok.Data;

@Data
public class EmailVerify {
    private String email;
    private String code;

    public EmailVerify() {
    }

    // 带参数的构造函数
    public EmailVerify(String email, String code) {
        this.email = email;
        this.code = code;
    }
}
