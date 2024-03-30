package com.example.service;

import com.example.controller.Result;
import com.example.domain.Schedule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 戴金磊
 * @since 2024-03-29
 */
public interface IScheduleService extends IService<Schedule> {

    Result selectWeeklySchedule();

    Result weeklyScheduleByDoctorID(Integer id);

    Result createSchedule(Schedule schedule);
}
