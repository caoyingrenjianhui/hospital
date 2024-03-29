package com.example.service;

import com.example.controller.Result;
import com.example.domain.Patient;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 戴金磊
 * @since 2024-03-24
 */
public interface IPatientService extends IService<Patient> {

    Result add(Patient patient);

    Result getAll();

    Result delete(Integer id);

    Result update(Patient patient);
}
