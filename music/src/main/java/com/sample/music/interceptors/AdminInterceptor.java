package com.sample.music.interceptors;

import com.sample.music.annotation.AdminOnly;
import com.sample.music.service.UserService;
import com.sample.music.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            // 注解 @AdminOnly，标记需要管理员权限的接口
            boolean methodHasAdminOnly = handlerMethod.getMethodAnnotation(AdminOnly.class) != null;
            boolean classHasAdminOnly = handlerMethod.getBeanType().isAnnotationPresent(AdminOnly.class);
            if (methodHasAdminOnly || classHasAdminOnly)
            {
                Map<String, Object> map = ThreadLocalUtil.get();
                Integer id = (Integer) map.get("id");
                Long userId = id.longValue();
                if (userService.isAdmin(userId)) {
                    System.out.println("isAdmin");
                    // 如果是管理员，放行
                    return true;
                } else {
                    System.out.println("noAdmin");
                    // 如果不是管理员，返回403 Forbidden
                    return false;
                }
            }
        }
        // 对于没有标记 @AdminOnly 的接口，放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        // 移除ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
