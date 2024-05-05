package com.example.service;

import com.example.controller.Result;
import com.example.domain.Prescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 戴金磊
 * @since 2024-04-18
 */
public interface IPrescriptionService extends IService<Prescription> {

    Result select(Prescription prescription);

    Result getMyPrescription();

    Result getProfitWithDate();
}
