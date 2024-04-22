package com.example.controller;


import com.example.domain.Schedule;
import com.example.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/weeklyScheduleByDoctorID/{id}")
    public Result weeklyScheduleByDoctorID(@PathVariable Integer id) {
        // 调用 service 方法来查询一周内的排班信息
        return scheduleService.weeklyScheduleByDoctorID(id);
    }

    @PostMapping("/add")
    public Result createSchedule(@RequestBody Schedule schedule) {
        return scheduleService.createSchedule(schedule);
    }

    @PostMapping("/update")
    public Result updateSchedule(@RequestBody Schedule schedule) {
        return scheduleService.update(schedule);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        return scheduleService.delete(id);
    }

    @PutMapping("/approve/{id}")
    public Result approve(@PathVariable Integer id){
        return scheduleService.approve(id);
    }

}

