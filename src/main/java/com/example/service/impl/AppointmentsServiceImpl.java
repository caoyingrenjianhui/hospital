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
import java.util.List;
import java.util.Map;

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
        appointmentsQueryWrapper.eq("userID",userID);
        List<Appointments> appointments = appointmentsDao.selectList(appointmentsQueryWrapper);
        return new Result(appointments, Code.GET_OK, "查询成功");
    }
}
