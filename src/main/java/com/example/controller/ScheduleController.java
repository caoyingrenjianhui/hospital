package com.example.controller;


import com.example.domain.Schedule;
import com.example.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 戴金磊
 * @since 2024-03-29
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private IScheduleService scheduleService;

    @GetMapping("/weeklySchedule")
    public Result selectWeeklySchedule() {
        // 调用 service 方法来查询一周内的排班信息
        return scheduleService.selectWeeklySchedule();
    }
}

