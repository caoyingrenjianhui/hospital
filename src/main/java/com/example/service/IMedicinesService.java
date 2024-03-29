package com.example.service;

import com.example.controller.Result;
import com.example.domain.Medicines;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 戴金磊
 * @since 2024-03-24
 */
public interface IMedicinesService extends IService<Medicines> {

    Result add(Medicines medicines);

    Result update(Medicines medicines);

    Result getAll();

    Result delete(Integer id);

    Result select(Medicines medicines);
}
