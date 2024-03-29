package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.controller.Code;
import com.example.controller.Result;
import com.example.dao.DoctorDao;
import com.example.dao.UserDao;
import com.example.domain.Doctor;
import com.example.domain.Patient;
import com.example.dao.PatientDao;
import com.example.domain.User;
import com.example.service.IPatientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 戴金磊
 * @since 2024-03-24
 */
@Service
public class PatientServiceImpl extends ServiceImpl<PatientDao, Patient> implements IPatientService {

    @Autowired
    private PatientDao patientDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DoctorDao doctorDao;

    @Override
    public Result add(Patient patient) {
        patient.setCreateTime(LocalDate.now().toString());
        int insert = patientDao.insert(patient);
        if (insert != 0) {
            return new Result(patient, Code.SAVE_OK, "新增成功");
        } else {
            return new Result(patient, Code.SAVE_ERR, "新增失败，请联系管理员");
        }
    }

    @Override
    public Result getAll() {
        List<Patient> list = patientDao.getAll();
        if (list != null) {
            for (Patient patient : list) {
                User user = userDao.selectById(patient.getUserID());
                patient.setUser(user);
                Doctor doctor = doctorDao.selectById(patient.getDoctorID());
                User selectById = userDao.selectById(doctor.getUserID());
                doctor.setUser(selectById);
                patient.setDoctor(doctor);
            }
        }
        return new Result(list, Code.GET_OK, "查询成功");
    }

    @Override
    public Result delete(Integer id) {
        Patient patient = patientDao.selectById(id);
        patient.setModifyTime(LocalDate.now().toString());
        patient.setIsdel(0);
        int i = patientDao.updateById(patient);
        if (i != 0) {
            return new Result(null, Code.DELETE_OK, "删除成功");
        } else {
            return new Result(null, Code.DELETE_ERR, "删除失败");
        }
    }

    @Override
    public Result update(Patient patient) {
        Patient selectById = patientDao.selectById(patient.getPatientID());
        if (selectById == null) {
            return new Result(null, Code.UPDATE_ERR, "无此病人的病例");
        }
        patient.setModifyTime(LocalDate.now().toString());
        patientDao.updateById(patient);
        return new Result(patient, Code.UPDATE_OK, "修改成功");
    }

    @Override
    public Result getPatient() {
//        获取医生id
        Map<String, Object> map = ThreadLocalUtil.get();
        String userID = (String) map.get("userID");
        Doctor doctor = doctorDao.selectByUserID(userID);
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctorID", doctor.getDoctorID());
        List<Patient> list = patientDao.selectList(queryWrapper);
        if (list != null) {
            for (Patient patient : list) {
                User user = userDao.selectById(patient.getUserID());
                patient.setUser(user);
                User selectById = userDao.selectById(doctor.getUserID());
                doctor.setUser(selectById);
                patient.setDoctor(doctor);
            }
        }
        return new Result(list, Code.GET_OK, "查询成功");
    }
}
