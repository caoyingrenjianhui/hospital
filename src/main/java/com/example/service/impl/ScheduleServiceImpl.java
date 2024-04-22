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
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private DoctorServiceImpl doctorService;

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
        doctorService.setDetail(schedules);
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
        wrapper.eq("ishandle", 1);
        List<Schedule> schedules = scheduleDao.selectList(wrapper);
        doctorService.setDetail(schedules);
        return new Result(schedules, Code.GET_OK, "查询成功");
    }

    @Override
    public Result createSchedule(Schedule schedule) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userID = (Integer) map.get("userID");
        Doctor doctor = doctorDao.selectByUserID(userID);
        schedule.setDoctorID(doctor.getDoctorID());
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
        QueryWrapper<Schedule> wrapper = new QueryWrapper<>();
        wrapper.eq("shift_date", schedule.getShiftDate());
        wrapper.eq("shift_type", schedule.getShiftType());
        wrapper.eq("doctorID", schedule.getDoctorID());
        List<Schedule> schedules = scheduleDao.selectList(wrapper);
        if (schedules.size() != 0) {
            return new Result(doctor, Code.SAVE_ERR, "新增失败，系统中已有相同排班");
        }
        int insert = scheduleDao.insert(schedule);
        if (insert != 0) {
            return new Result(doctor, Code.SAVE_OK, "新增成功");
        } else {
            return new Result(doctor, Code.SAVE_ERR, "新增失败，请联系管理员");
        }
    }

    @Override
    public Result update(Schedule schedule) {
        int i = scheduleDao.updateById(schedule);
        if (i != 0) {
            return new Result(null, Code.UPDATE_OK, "修改成功");
        } else {
            return new Result(null, Code.UPDATE_ERR, "修改失败");
        }
    }

    @Override
    public Result delete(Integer id) {
        Schedule schedule = scheduleDao.selectById(id);
        schedule.setModifyTime(LocalDate.now().toString());
        int update = scheduleDao.updateById(schedule);
        int i = scheduleDao.deleteById(id);
        if (i != 0) {
            return new Result(null, Code.DELETE_OK, "删除成功");
        } else {
            return new Result(null, Code.DELETE_ERR, "删除失败");
        }
    }

    @Override
    public Result approve(Integer id) {
        Schedule schedule = scheduleDao.selectById(id);
        schedule.setModifyTime(LocalDate.now().toString());
        schedule.setIshandle(1);
        int i = scheduleDao.updateById(schedule);
        if (i != 0) {
            return new Result(null, Code.UPDATE_OK, "批准成功");
        } else {
            return new Result(null, Code.UPDATE_ERR, "批准失败");
        }
    }
}
