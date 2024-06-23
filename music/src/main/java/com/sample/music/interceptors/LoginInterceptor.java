package com.sample.music.interceptors;

import com.sample.music.utils.JwtUtil;
import com.sample.music.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.Objects;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        // 令牌验证
        String token = request.getHeader("Authorization").substring(7);
        // 验证token
/*        try {
            // 从redis中获取相同的token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);
            if(redisToken == null){
                // token已经失效了
                throw new RuntimeException();
            }

            Map<String, Object> claims = jwtUtil.parseToken(token);
            // 把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            // 放行
            return true;
        } catch (Exception e) {
            // http响应码为401
            response.setStatus(401);
            // 不放行
            return false;
        }*/
        // 从请求中获取用户ID，
        Map<String, Object> claims = jwtUtil.parseToken(token);
        if(claims == null || !claims.containsKey("id")) {
            // 如果无法解析令牌或令牌中不包含用户ID，返回401错误
            response.setStatus(401);
            return false;
        }
        Integer userId = (Integer) claims.get("id");

        // 构造Redis中的键名
        String redisKey = "token_" + userId;

        // 从redis中获取相同的token
        ValueOperations<String, String> operation = stringRedisTemplate.opsForValue();
        String redisToken = operation.get("token:"+redisKey);

        // 验证从Redis中获取的token是否与请求中的token相同
        if (token.equals(redisToken)) {
            // 令牌匹配，放行
            // 把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            return true;
        } else {
            // 令牌不匹配，返回401错误
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
