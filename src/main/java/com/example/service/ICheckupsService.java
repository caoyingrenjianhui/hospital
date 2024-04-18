package com.example.service;

import com.example.controller.Result;
import com.example.domain.Checkups;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 戴金磊
 * @since 2024-04-18
 */
public interface ICheckupsService extends IService<Checkups> {

    Result select(Checkups checkups);

    Result getCheckups();

    Result add(Checkups checkups);

    Result update(Checkups checkups);

    Result delete(Integer id);
}
