package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.controller.Code;
import com.example.controller.Result;
import com.example.dao.DoctorDao;
import com.example.domain.Doctor;
import com.example.domain.Schedule;
import com.example.dao.ScheduleDao;
import com.example.domain.UserType;
import com.example.service.IScheduleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    @Autowired
    private DoctorDao doctorDao;

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
                .ge("shift_date", startDateStr)
                .le("shift_date", endDateStr));
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
        wrapper.eq("doctorID", id);
        List<Schedule> schedules = scheduleDao.selectList(wrapper);
        return new Result(schedules, Code.GET_OK, "查询成功");
    }

    @Override
    public Result createSchedule(Schedule schedule) {
        Doctor doctor = doctorDao.selectById(schedule.getDoctorID());
        if (doctor == null) {
            return new Result(null, Code.SAVE_ERR, "新增失败，系统中无此医生");
        }
        schedule.setCreateTime(LocalDate.now().toString());
        schedule.setModifyTime(null);
        try {
            // 解析前端传递的日期字符串
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date date = inputFormat.parse(schedule.getShiftDate());
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate localDate = instant.atZone(zoneId).toLocalDate();
            LocalDate nextDay = localDate.plusDays(1);
            Date nextDate = Date.from(nextDay.atStartOfDay(zoneId).toInstant());
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nextDayFormatted = outputFormat.format(nextDate);
            schedule.setShiftDate(nextDayFormatted);
        } catch (Exception e) {
            System.out.println("日期转换失败：" + e.getMessage());
        }
        int insert = scheduleDao.insert(schedule);
        if (insert != 0) {
            return new Result(doctor, Code.SAVE_OK, "新增成功");
        } else {
            return new Result(doctor, Code.SAVE_ERR, "新增失败，请联系管理员");
        }
    }
}
