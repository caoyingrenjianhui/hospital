package com.example.interceptors;

import com.example.utils.JwtUtil;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        令牌验证
        String token = request.getHeader("Authorization");
        try {
//            从redis中获取相同的token
            ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
            String redisToken = opsForValue.get(token);
            if (redisToken == null) {
                throw new RuntimeException();
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);
//            把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);

            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        清空ThreadLocal中的数据，防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
