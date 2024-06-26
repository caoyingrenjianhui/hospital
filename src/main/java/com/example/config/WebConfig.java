package com.example.config;

import com.example.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//       不拦截
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login", "/user/reset",
                "/user/register", "/pages/*", "/js/*", "/css/*", "/images/*", "/webfonts/*", "/element-ui/**", "/medicines/upload", "/upload/*");
    }
}
