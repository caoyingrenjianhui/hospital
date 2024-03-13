package com.example.service;

import com.example.controller.Result;
import com.example.domain.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 */
public interface IDishService extends IService<Dish> {

    Result add(Dish dish);

    Result update(Dish dish);
}
