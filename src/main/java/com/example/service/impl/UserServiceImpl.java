package com.example.service.impl;

import com.example.controller.Code;
import com.example.controller.Result;
import com.example.dao.UserDao;
import com.example.domain.User;
import com.example.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.JwtUtil;
import com.example.utils.Md5Util;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result register(User user) {
        User byId = userDao.selectById(user.getUserID());
        if (byId != null) {
            return new Result(user, Code.SAVE_ERR, "此学号已经注册过");
        }
        String md5String;
        try {
            md5String = Md5Util.getMD5String(user.getPassword());
        } catch (Exception e) {
            return new Result(user, Code.SAVE_ERR, "密码加密失败");
        }
        user.setPassword(md5String);
        user.setCreateTime(LocalDate.now().toString());
        int insert = userDao.insert(user);
        if (insert != 0) {
            return new Result(user, Code.SAVE_OK, "注册成功");
        } else {
            return new Result(user, Code.SAVE_ERR, "注册失败");
        }
    }

    @Override
    public Result getAllUser() {
        List<User> list = userDao.getAllUser();
        return new Result(list, Code.GET_OK, "查询成功");
    }

    @Override
    public Result login(User user) {
        User selectById = userDao.selectById(user.getUserID());
        if (selectById == null) {
            return new Result(null, Code.GET_ERR, "用户未注册");
        }
        String md5String = Md5Util.getMD5String(user.getPassword());
        if (!selectById.getPassword().equals(md5String)) {
            return new Result(null, Code.GET_ERR, "密码错误");
        }

//        登录成功，生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userID", user.getUserID());
        claims.put("username", user.getUsername());
        String token = JwtUtil.genToken(claims);
//        把token存储到redis中
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        opsForValue.set(token,token,1, TimeUnit.HOURS);
        return new Result(token, Code.GET_OK, "登录成功");
    }

    @Override
    public Result userInfo() {
        Map<String, Object> map = ThreadLocalUtil.get();
        String userID = (String) map.get("userID");
        User user = userDao.selectById(userID);
        return new Result(user, Code.GET_OK, "查询成功");
    }

    @Override
    public Result update(User user) {
        user.setModifyTime(LocalDate.now().toString());
        userDao.update(user);
        return new Result(user, Code.UPDATE_OK, "修改成功");
    }

    @Override
    public Result updatePhoto(String photo) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String userID = (String) map.get("userID");
        userDao.updatePhoto(photo, userID);
        return new Result(null, Code.UPDATE_OK, "头像修改成功");
    }

    @Override
    public Result updatePassword(User user,String token) {
        if (!StringUtils.hasLength(user.getPassword())
                || !StringUtils.hasLength(user.getOldPassword())
                || !StringUtils.hasLength(user.getRePassword())) {
            return new Result(null, Code.UPDATE_ERR, "缺少必要的参数");
        } else if (!user.getPassword().equals(user.getRePassword())) {
            return new Result(null, Code.UPDATE_ERR, "两次输入密码的新密码不一致");
        }
        Map<String, Object> map = ThreadLocalUtil.get();
        String userID = (String) map.get("userID");
        User selectById = userDao.selectById(userID);
        if (selectById.getPassword().equals(Md5Util.getMD5String(user.getOldPassword()))) {
            userDao.updatePassword(Md5Util.getMD5String(user.getPassword()), userID);
//            删除redis中的token
            ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
            opsForValue.getOperations().delete(token);
            return new Result(user, Code.UPDATE_OK, "密码修改成功");
        } else {
            return new Result(null, Code.UPDATE_ERR, "原密码不正确");
        }
    }
}
