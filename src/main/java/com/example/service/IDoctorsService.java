package com.example.service;

import com.example.controller.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.Doctor;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 戴金磊
 * @since 2024-03-24
 */
public interface IDoctorsService extends IService<Doctor> {

    Result add(Doctor doctor);

    Result getAll();

    Result update(Doctor doctor);

    Result delete(Integer id);

    Result select(Doctor doctor);
}
