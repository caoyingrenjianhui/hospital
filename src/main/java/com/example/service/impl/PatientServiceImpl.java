package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.controller.Code;
import com.example.controller.Result;
import com.example.dao.DoctorDao;
import com.example.dao.UserDao;
import com.example.domain.Doctor;
import com.example.domain.Medicines;
import com.example.domain.Patient;
import com.example.dao.PatientDao;
import com.example.domain.User;
import com.example.service.IPatientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
        User user = userDao.selectById(patient.getUserID());
        if (user == null) {
            return new Result(patient, Code.SAVE_ERR, "新增失败，系统中无此人员，请先自行注册或检查身份证号是否正确");
        }
//        QueryWrapper<Patient> patientQueryWrapper = new QueryWrapper<>();
//        patientQueryWrapper.eq("userID", patient.getUserID());
//        Patient p = patientDao.selectOne(patientQueryWrapper);
//        if (p != null) {
//            return new Result(patient, Code.SAVE_ERR, "新增失败，系统中已经有此患者，请先自行注册或检查身份证号是否正确");
//        }
        QueryWrapper<Doctor> wrapper = new QueryWrapper<>();
        Map<String, Object> map = ThreadLocalUtil.get();
        String userID = (String) map.get("userID");
        wrapper.eq("userID", userID);
        Doctor doctor = doctorDao.selectOne(wrapper);
        patient.setDoctorID(doctor.getDoctorID());
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
        int update = patientDao.updateById(patient);
        int i = patientDao.deleteById(id);
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

    @Override
    public Result select(Patient patient) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        if (patient.getUser().getUsername() != null && !"".equals(patient.getUser().getUsername())) {
            userWrapper.like("username", patient.getUser().getUsername());
        }
        List<User> users = userDao.selectList(userWrapper);

        QueryWrapper<Patient> wrapper = new QueryWrapper<>();
        if (users != null) {
            List<String> userIds = new ArrayList<>();
            for (User user : users) {
                userIds.add(user.getUserID());
            }
            wrapper.in("userID", userIds);
        }
        if (patient.getPatientID() != null) {
            wrapper.eq("patientID", patient.getPatientID());
        }
        if (patient.getDoctorID() != null && !"".equals(patient.getDoctorID())) {
            wrapper.eq("doctorID", patient.getDoctorID());
        }
        if (patient.getUserID() != null && !"".equals(patient.getUserID())) {
            wrapper.eq("userID", patient.getUserID());
        }
        List<Patient> list = patientDao.selectList(wrapper);
        if (list == null) {
            return new Result(null, Code.GET_ERR, "查询不到数据");
        }
        for (Patient p : list) {
            User user = userDao.selectById(p.getUserID());
            p.setUser(user);
        }
        return new Result(list, Code.GET_OK, "查询成功");
    }
}
