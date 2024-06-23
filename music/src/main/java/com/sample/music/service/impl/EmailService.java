package com.sample.music.service.impl;

import com.sample.music.domain.vo.EmailVerify;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /** 发送验证码
     * @param email 邮箱
     * @return EmailVerify
     */
    public EmailVerify sendVerificationEmail(String email) {

        String code = this.generateVerificationCode();

        EmailVerify emailVerify = new EmailVerify();
        emailVerify.setEmail(email);
        emailVerify.setCode(code);


        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(username);
        mail.setTo(email);
        mail.setSubject("Password Reset Verification Code");
        mail.setText("Your verification code is: " + code);

        this.saveVerifyCode(emailVerify);

        javaMailSender.send(mail);
        return emailVerify;
    }

    /**
     * 生成验证码
     * @return String
     */
    private String generateVerificationCode() {
        // 这里可以生成一个随机的验证码
        return RandomStringUtils.randomNumeric(6);
    }

    /**
     * 将验证码存入redis
     * @param emailVerify 邮箱与验证码
     */
    private void saveVerifyCode(EmailVerify emailVerify){
        String code = emailVerify.getCode();
        String email = emailVerify.getEmail();
        long expire = TimeUnit.MINUTES.toMillis(5);
        stringRedisTemplate.opsForValue().set("verify:"+email, code, expire, TimeUnit.MILLISECONDS);
    }

    /** 验证验证码
     * @param email 邮箱
     * @return boolean
     */
    public boolean validateVerifyCode(String email, String submittedCode) {
        String storedCode = stringRedisTemplate.opsForValue().get("verify:"+email);

        return storedCode != null && storedCode.equals(submittedCode);
    }

    /**
     * 删除验证码
     * @param email 邮箱
     */
    public void deleteVerifyCode(String email){
        stringRedisTemplate.delete(email);
    }
}
