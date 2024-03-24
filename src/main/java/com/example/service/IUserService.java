package com.example.service;

import com.example.controller.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 */
public interface IUserService extends IService<User> {

    Result register(User user);

    Result getAllUser();

    Result login(User user);

    Result userInfo();

    Result update(User user);

    Result updatePhoto(String photo);

    Result updatePassword(User user,String token);
}
