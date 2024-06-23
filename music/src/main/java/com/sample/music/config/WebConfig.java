package com.sample.music.config;

import com.sample.music.interceptors.AdminInterceptor;
import com.sample.music.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(loginInterceptor)
                .excludePathPatterns("/api/user/login","/api/user/register");
        registry
                .addInterceptor(adminInterceptor)
                .addPathPatterns("/**");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有路径
                .allowedOrigins("http://localhost:63342") // 允许指定域名访问
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // 允许的方法
                .allowedHeaders("*") // 允许的头信息
                .allowCredentials(true); // 允许认证信息
    }
}
