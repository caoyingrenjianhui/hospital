package com.example.service;

import com.example.controller.Result;
import com.example.domain.Appointments;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 戴金磊
 * @since 2024-03-31
 */
public interface IAppointmentsService extends IService<Appointments> {

    Result add(Appointments appointments);
}
