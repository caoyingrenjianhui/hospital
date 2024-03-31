package com.example.service.impl;

import com.example.controller.Code;
import com.example.controller.Result;
import com.example.dao.DoctorDao;
import com.example.domain.Appointments;
import com.example.dao.AppointmentsDao;
import com.example.domain.Doctor;
import com.example.service.IAppointmentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
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
        int insert = appointmentsDao.insert(appointments);
        if (insert != 0) {
            return new Result(doctor, Code.SAVE_OK, "预约成功");
        } else {
            return new Result(doctor, Code.SAVE_ERR, "新增失败，请联系管理员");
        }
    }
}
