package com.example.service;

import com.example.controller.Result;
import com.example.domain.Orderitem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 */
public interface IOrderitemService extends IService<Orderitem> {

    Result add(Orderitem orderitem);

    Result finish(Integer id);
}
