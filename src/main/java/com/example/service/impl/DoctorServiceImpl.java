package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.controller.Code;
import com.example.controller.Result;
import com.example.dao.DoctorDao;
import com.example.dao.ScheduleDao;
import com.example.dao.UserDao;
import com.example.domain.*;
import com.example.service.IDoctorsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 戴金磊
 * @since 2024-03-24
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorDao, Doctor> implements IDoctorsService {
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ScheduleDao scheduleDao;

    @Override
    public Result add(Doctor doctor) {
        User user = userDao.selectById(doctor.getUserID());
        if (user == null) {
            return new Result(doctor, Code.SAVE_ERR, "新增失败，系统中无此人员，请先自行注册或检查账号是否正确");
        }
        QueryWrapper<Doctor> patientQueryWrapper = new QueryWrapper<>();
        patientQueryWrapper.eq("userID", doctor.getUserID());
        Doctor p = doctorDao.selectOne(patientQueryWrapper);
        if (p != null) {
            return new Result(doctor, Code.SAVE_ERR, "新增失败，系统中已经有此医生");
        }
        doctor.setCreateTime(LocalDate.now().toString());
        doctor.setDepartment(Department.getCodeByName(doctor.getDepartment()) + "");
        int insert = doctorDao.insert(doctor);
        if (insert != 0) {
            user.setUserType(UserType.doctor.getCode());
            userDao.updateById(user);
            return new Result(doctor, Code.SAVE_OK, "新增成功");
        } else {
            return new Result(doctor, Code.SAVE_ERR, "新增失败，请联系管理员");
        }
    }

    @Override
    public Result update(Doctor doctor) {
        Doctor selectById = doctorDao.selectById(doctor.getDoctorID());
        if (selectById == null) {
            return new Result(null, Code.UPDATE_ERR, "无此医生");
        }
        doctor.setModifyTime(LocalDate.now().toString());
        doctor.setDepartment(Department.getCodeByName(doctor.getDepartment()) + "");
        doctorDao.updateById(doctor);
        return new Result(doctor, Code.UPDATE_OK, "修改成功");
    }

    @Override
    public Result delete(Integer id) {
        Doctor doctor = doctorDao.selectById(id);
        doctor.setModifyTime(LocalDate.now().toString());
        int update = doctorDao.updateById(doctor);
        int i = doctorDao.deleteById(id);
        if (i != 0) {
            User user = userDao.selectById(doctor.getUserID());
            user.setUserType(UserType.common.getCode());
            userDao.updateById(user);
            return new Result(null, Code.DELETE_OK, "删除成功");
        } else {
            return new Result(null, Code.DELETE_ERR, "删除失败");
        }
    }

    @Override
    public Result select(Doctor doctor) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        if (doctor.getUser().getUsername() != null && !"".equals(doctor.getUser().getUsername())) {
            userWrapper.like("username", doctor.getUser().getUsername());
        }
        List<User> users = userDao.selectList(userWrapper);

        QueryWrapper<Doctor> wrapper = new QueryWrapper<>();
        if (users != null) {
            List<Integer> userIds = new ArrayList<>();
            for (User user : users) {
                userIds.add(user.getUserID());
            }
            wrapper.in("userID", userIds);
        }
        if (doctor.getDoctorID() != null && !"".equals(doctor.getDoctorID())) {
            wrapper.eq("doctorID", doctor.getDoctorID());
        }
        if (doctor.getUserID() != null && !"".equals(doctor.getUserID())) {
            wrapper.eq("userID", doctor.getUserID());
        }
        if (doctor.getDepartment() != null && !"".equals(doctor.getDepartment())) {
            wrapper.eq("department", Department.getCodeByName(doctor.getDepartment()) + "");
        }
        List<Doctor> list = doctorDao.selectList(wrapper);
        if (list == null) {
            return new Result(null, Code.GET_ERR, "查询不到数据");
        }
        for (Doctor d : list) {
            User user = userDao.selectById(d.getUserID());
            d.setUser(user);
            d.setDepartment(Department.getNameByCode(Integer.valueOf(d.getDepartment())));
        }
        return new Result(list, Code.GET_OK, "查询成功");
    }

    @Override
    public Result selectAppointment(Doctor doctor) {
//        先根据科室查询
        QueryWrapper<Doctor> wrapper = new QueryWrapper<>();
        if (doctor.getDepartment() != null) {
            wrapper.eq("department", Department.getCodeByName(doctor.getDepartment()));
        }
        List<Doctor> doctors = doctorDao.selectList(wrapper);
        List<Integer> doctorIds = doctors.stream()
                .map(d -> d.getDoctorID()) // 使用 lambda 表达式获取医生 ID
                .collect(Collectors.toList());
//        根据日期查
        QueryWrapper<Schedule> scheduleQueryWrapper = new QueryWrapper<>();
        if (doctor.getShiftDate() != null) {
            setDate(doctor);
            scheduleQueryWrapper.eq("shift_date", doctor.getShiftDate());
        } else {
            // 获取当前日期
            LocalDate currentDate = LocalDate.now();
            // 计算一周后的日期
            LocalDate endDate = currentDate.plusDays(7);
            // 转换日期格式为字符串，假设您的日期格式是 "yyyy-MM-dd"
            String startDateStr = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String endDateStr = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            scheduleQueryWrapper.ge("shift_date", startDateStr);
            scheduleQueryWrapper.le("shift_date", endDateStr);
        }
        if (doctor.getShiftType() != null) {
            scheduleQueryWrapper.eq("shift_type", doctor.getShiftType());
        }
        if (doctorIds.size() == 0) {
            return new Result(null, Code.GET_ERR, "未查询到排班信息");
        }
        scheduleQueryWrapper.in("doctorID", doctorIds);
        List<Schedule> schedules = scheduleDao.selectList(scheduleQueryWrapper);
        if (schedules.size() == 0) {
            return new Result(null, Code.GET_ERR, "未查询到排班信息");
        }
        setDetail(schedules);
        return new Result(schedules, Code.GET_OK, "查询成功");
    }

    private void setDate(Doctor doctor) {
        try {
            // 解析前端传递的日期字符串
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date date = inputFormat.parse(doctor.getShiftDate());
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate localDate = instant.atZone(zoneId).toLocalDate();
            LocalDate nextDay = localDate.plusDays(1);
            Date nextDate = Date.from(nextDay.atStartOfDay(zoneId).toInstant());
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nextDayFormatted = outputFormat.format(nextDate);
            doctor.setShiftDate(nextDayFormatted);
        } catch (Exception e) {
            System.out.println("日期转换失败：" + e.getMessage());
        }
    }

    public void setDetail(List<Schedule> schedules) {
        for (Schedule s : schedules) {
            Doctor doctor = doctorDao.selectById(s.getDoctorID());
            User user = userDao.selectById(doctor.getUserID());
            doctor.setUser(user);
            doctor.setDepartment(Department.getNameByCode(Integer.valueOf(doctor.getDepartment())));
            s.setDoctor(doctor);
        }
    }

    @Override
    public Result getAll() {
        List<Doctor> list = doctorDao.getAll();
        if (list != null) {
            for (Doctor d : list) {
                User user = userDao.selectById(d.getUserID());
                d.setUser(user);
                d.setDepartment(Department.getNameByCode(Integer.parseInt(d.getDepartment())));
            }
        }
        return new Result(list, Code.GET_OK, "查询成功");
    }
}
