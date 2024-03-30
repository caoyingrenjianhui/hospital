package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.controller.Code;
import com.example.controller.Result;
import com.example.dao.DoctorDao;
import com.example.dao.UserDao;
import com.example.domain.*;
import com.example.service.IDoctorsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    @Override
    public Result add(Doctor doctor) {
        User user = userDao.selectById(doctor.getUserID());
        if (user == null) {
            return new Result(doctor, Code.SAVE_ERR, "新增失败，系统中无此人员，请先自行注册或检查身份证号是否正确");
        }
        QueryWrapper<Doctor> patientQueryWrapper = new QueryWrapper<>();
        patientQueryWrapper.eq("userID", doctor.getUserID());
        Doctor p = doctorDao.selectOne(patientQueryWrapper);
        if (p != null) {
            return new Result(doctor, Code.SAVE_ERR, "新增失败，系统中已经有此医生");
        }
        doctor.setCreateTime(LocalDate.now().toString());
        doctor.setIsdel(0);
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
        doctorDao.updateById(doctor);
        return new Result(doctor, Code.UPDATE_OK, "修改成功");
    }

    @Override
    public Result delete(Integer id) {
        Doctor doctor = doctorDao.selectById(id);
        doctor.setModifyTime(LocalDate.now().toString());
        doctor.setIsdel(0);
        int i = doctorDao.updateById(doctor);
        if (i != 0) {
            return new Result(null, Code.DELETE_OK, "删除成功");
        } else {
            return new Result(null, Code.DELETE_ERR, "删除失败");
        }
    }

    @Override
    public Result select(Doctor doctor) {
        QueryWrapper<Doctor> wrapper = new QueryWrapper<>();
        if (doctor.getDoctorID() != null && !"".equals(doctor.getDoctorID())) {
            wrapper.eq("doctorID", doctor.getDoctorID());
        }
        if (doctor.getUserID() != null && !"".equals(doctor.getUserID())) {
            wrapper.eq("userID", doctor.getUserID());
        }
        if (doctor.getDepartment() != null && !"".equals(doctor.getDepartment())) {
            wrapper.eq("department", Department.getCodeByName(doctor.getDepartment()));
        }
        List<Doctor> list = doctorDao.selectList(wrapper);
        if (list == null) {
            return new Result(null, Code.GET_ERR, "查询不到数据");
        }
        for (Doctor d : list) {
            QueryWrapper<User> userWrapper = new QueryWrapper<>();
            if (doctor.getUser().getUsername() != null && !"".equals(doctor.getUser().getUsername())) {
                userWrapper.like("username", doctor.getUser().getUsername());
            }
            if (doctor.getUserID() != null) {
                userWrapper.eq("userID", doctor.getUserID());
            }
            User user = userDao.selectOne(userWrapper);
            d.setUser(user);
        }
        return new Result(list, Code.GET_OK, "查询成功");
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
