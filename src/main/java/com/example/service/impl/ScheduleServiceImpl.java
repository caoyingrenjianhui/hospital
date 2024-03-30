package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.controller.Code;
import com.example.controller.Result;
import com.example.domain.Schedule;
import com.example.dao.ScheduleDao;
import com.example.service.IScheduleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 戴金磊
 * @since 2024-03-29
 */
@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleDao, Schedule> implements IScheduleService {

    @Autowired
    private ScheduleDao scheduleDao;

    @Override
    public Result selectWeeklySchedule() {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 计算一周后的日期
        LocalDate endDate = currentDate.plusDays(7);

        // 转换日期格式为字符串，假设您的日期格式是 "yyyy-MM-dd"
        String startDateStr = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDateStr = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // 调用 MyBatis-Plus 的方法来查询一周内的排班信息
        List<Schedule> schedules = scheduleDao.selectList(new QueryWrapper<Schedule>()
                .between("shift_date", startDateStr, endDateStr));
        return new Result(schedules, Code.GET_OK, "查询成功");
    }

    @Override
    public Result weeklyScheduleByDoctorID(Integer id) {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 计算一周后的日期
        LocalDate endDate = currentDate.plusDays(7);

        // 转换日期格式为字符串，假设您的日期格式是 "yyyy-MM-dd"
        String startDateStr = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDateStr = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // 调用 MyBatis-Plus 的方法来查询一周内的排班信息
        QueryWrapper<Schedule> wrapper = new QueryWrapper<>();
        wrapper.between("shift_date", startDateStr, endDateStr);
        wrapper.eq("doctorID",id);
        List<Schedule> schedules = scheduleDao.selectList(wrapper);
        return new Result(schedules, Code.GET_OK, "查询成功");
    }
}
