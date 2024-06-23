package com.sample.music.aspect;

import com.sample.music.utils.ThreadLocalUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;


@Aspect
@Component
public class UserOperationLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(UserOperationLogAspect.class);

    @Before("execution(* com.sample.music.controller.*.*(..))")
    public void logUserOperation(JoinPoint joinPoint) {
        logger.debug("Entering logUserOperation method");
        Map<String, Object> claims = ThreadLocalUtil.get();
        if (claims != null) {
            String username = (String) claims.get("username");
            // 如果claims中包含用户名，则记录操作日志
            if (username != null) {
                logger.info("用户[{}] 执行了操作：{} 在 {}", username, joinPoint.getSignature().getName(), joinPoint.getSourceLocation());
            } else {
                // 如果claims不包含用户名，记录匿名用户操作
                logger.info("匿名用户执行了操作：{} 在 {}", joinPoint.getSignature().getName(), joinPoint.getSourceLocation());
            }
        } else {
            // 如果ThreadLocalUtil.get()返回null，记录未认证用户操作
            logger.info("未认证用户执行了操作：{} 在 {}", joinPoint.getSignature().getName(), joinPoint.getSourceLocation());
        }
    }
}
