package com.example.service;

import com.example.controller.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.Category;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 */
public interface ICategoryService extends IService<Category> {

    Result add(Category category);

    Result getAll();

    Result delete(Integer categoryID);

    Result update(Category category);
}
