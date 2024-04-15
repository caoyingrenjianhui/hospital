package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.controller.Code;
import com.example.controller.Result;
import com.example.dao.DoctorDao;
import com.example.domain.*;
import com.example.dao.AppointmentsDao;
import com.example.service.IAppointmentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 戴金磊
 * @since 2024-03-31
 */
@Service
public class AppointmentsServiceImpl extends ServiceImpl<AppointmentsDao, Appointments> implements IAppointmentsService {

    @Autowired
    private AppointmentsDao appointmentsDao;
    @Autowired
    private DoctorDao doctorDao;

    @Override
    public Result add(Appointments appointments) {
        Doctor doctor = doctorDao.selectById(appointments.getDoctorID());
        if (doctor == null) {
            return new Result(null, Code.SAVE_ERR, "新增失败，系统中无此医生");
        }
//        限制预约个数
        QueryWrapper<Appointments> wrapper = new QueryWrapper<>();
        wrapper.eq("doctorID", appointments.getDoctorID());
        wrapper.eq("userID", appointments.getUserID());
        wrapper.eq("appointment_date", appointments.getAppointmentDate());
        List<Appointments> list = appointmentsDao.selectList(wrapper);
        if (list.size() != 0) {
            return new Result(doctor, Code.SAVE_ERR, "预约失败，已有相同预约记录");
        }
        QueryWrapper<Appointments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctorID", appointments.getDoctorID());
        queryWrapper.eq("appointment_date", appointments.getAppointmentDate());
        List<Appointments> selectList = appointmentsDao.selectList(queryWrapper);
        if (selectList.size() >= 10) {
            return new Result(doctor, Code.SAVE_ERR, "预约失败，该医生当天已约满");
        }
        int insert = appointmentsDao.insert(appointments);
        if (insert != 0) {
            return new Result(doctor, Code.SAVE_OK, "预约成功");
        } else {
            return new Result(doctor, Code.SAVE_ERR, "新增失败，请联系管理员");
        }
    }

    @Override
    public Result update(Appointments appointments) {
        int update = appointmentsDao.updateById(appointments);
        return new Result(appointments, Code.UPDATE_OK, "修改成功");
    }

    @Override
    public Result delete(Integer id) {
        int i = appointmentsDao.deleteById(id);
        if (i != 0) {
            return new Result(null, Code.DELETE_OK, "删除成功");
        } else {
            return new Result(null, Code.DELETE_ERR, "删除失败");
        }
    }

    @Override
    public Result getAll() {
        QueryWrapper<Appointments> appointmentsQueryWrapper = new QueryWrapper<>();
        Map<String, Object> map = ThreadLocalUtil.get();
        String userID = (String) map.get("userID");
        appointmentsQueryWrapper.eq("userID", userID);
        List<Appointments> appointments = appointmentsDao.selectList(appointmentsQueryWrapper);
        return new Result(appointments, Code.GET_OK, "查询成功");
    }

    @Override
    public Result getMyAppointment() {
        QueryWrapper<Doctor> appointmentsQueryWrapper = new QueryWrapper<>();
        Map<String, Object> map = ThreadLocalUtil.get();
        String userID = (String) map.get("userID");
        appointmentsQueryWrapper.eq("userID", userID);
        Doctor doctor = doctorDao.selectOne(appointmentsQueryWrapper);
        if (doctor == null) {
            return new Result(null, Code.GET_ERR, "您还不是医生");
        }
        QueryWrapper<Appointments> wrapper = new QueryWrapper<>();
        wrapper.eq("doctorID", doctor.getDoctorID());
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 计算一周后的日期
        LocalDate endDate = currentDate.plusDays(7);
        // 转换日期格式为字符串，假设您的日期格式是 "yyyy-MM-dd"
        String startDateStr = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDateStr = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        wrapper.between("appointment_date", startDateStr, endDateStr);
        List<Appointments> appointments = appointmentsDao.selectList(wrapper);
        return new Result(appointments, Code.GET_OK, "查询成功");
    }
}
