package com.sample.music.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    @Value("${spring.security.jwt.key}")
    private String key;

    @Value("${spring.security.jwt.expire}")
    private int expire;

    @Resource
    private StringRedisTemplate template;

    /**
     * 接受业务数据，生成token并返回
     * @param claims 用户信息
     * @return 是否操作成功
     */
    public String genToken(Map<String, Object> claims){
        Date expire = this.expireTime();
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(expire)
                .sign(Algorithm.HMAC256(key));
    }

    /**
     * 接受token，验证token，并返回业务数据
     * @param token 令牌
     * @return 是否操作成功
     */
    public Map<String, Object> parseToken(String token){
        try {
            return JWT.require(Algorithm.HMAC256(key))
                    .build()
                    .verify(token)
                    .getClaim("claims")
                    .asMap();
        } catch (JWTVerificationException e) {
            // 令牌无效时，返回 null 或抛出异常
            return null;
        }
    }
    /**
     * 让指定Jwt令牌失效
     * @return 过期时间：7天
     */
    public Date expireTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, expire * 24);
        return calendar.getTime();
    }

    /**
     * 使Jwt令牌去掉Bearer
     * @param headToken 请求头中携带的令牌
     * @return Jwt令牌
     */
    public String convertToken(String headToken){
        if(headToken == null || !headToken.startsWith("Bearer ")){
            return null;
        }
        return headToken.substring(7);
    }

    /**
     * 让指定Jwt令牌失效
     * @param headToken 请求头中携带的令牌
     * @return 是否操作成功
     */
    public boolean invalidateJwt(String headToken){
        String token = this.convertToken(headToken);
        Map<String, Object> claims = parseToken(token);
        if (claims == null || !claims.containsKey("id")) {
            return false;
        }
        // 使用从令牌中解析出的 ID 来构造黑名单键
        String userToken = "token_" + claims.get("id");
        return banToken(userToken);
    }

    /**
     * 将Token列入Redis黑名单中
     * @param userToken 令牌ID
     * @return 是否操作成功
     */
    private boolean banToken(String userToken) {
        // 检查令牌是否已经在黑名单中
        if (isInvalidToken(userToken)) {
            return false;
        }
        // 令牌的过期时间
        long expire = TimeUnit.HOURS.toMillis(7);
        // 将黑名单项设置为过期时间
        template.opsForValue().set("jwt:blacklist:" + userToken, "ban", expire, TimeUnit.MILLISECONDS);
        return true;
    }

    /**
     * 验证Token是否被列入Redis黑名单
     * @param userToken 令牌ID
     * @return 是否操作成功
     */
    private boolean isInvalidToken(String userToken){
        return Boolean.TRUE.equals(template.hasKey("jwt:blacklist:" + userToken));
    }
}
