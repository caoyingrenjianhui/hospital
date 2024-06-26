package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.controller.Code;
import com.example.controller.Result;
import com.example.dao.*;
import com.example.domain.*;
import com.example.service.IMedicinesService;
import com.example.service.IPatientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @Autowired
    private MedicinesDao medicinesDao;
    @Autowired
    private IMedicinesService medicinesService;
    @Autowired
    private PrescriptionDao prescriptionDao;

    @Override
    public Result add(Patient patient) {
        User user = userDao.selectById(patient.getUserID());
        if (user == null) {
            return new Result(patient, Code.SAVE_ERR, "新增失败，系统中无此人员，请先自行注册或检查账号是否正确");
        }
//        QueryWrapper<Patient> patientQueryWrapper = new QueryWrapper<>();
//        patientQueryWrapper.eq("userID", patient.getUserID());
//        Patient p = patientDao.selectOne(patientQueryWrapper);
//        if (p != null) {
//            return new Result(patient, Code.SAVE_ERR, "新增失败，系统中已经有此患者，请先自行注册或检查账号是否正确");
//        }
        QueryWrapper<Doctor> wrapper = new QueryWrapper<>();
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userID = (Integer) map.get("userID");
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
        extracted(list);
        return new Result(list, Code.GET_OK, "查询成功");
    }

    private void setMedicines(Patient patient) {
        List<Medicines> medicines = new ArrayList<>();
        if (patient.getMedicine() != null) {
            String[] split = patient.getMedicine().split(";");
            for (String medicineInfo : split) {
                String[] parts = medicineInfo.split(":");
                if (parts.length == 3) {
                    String id = parts[0];
                    int quantity = Integer.parseInt(parts[2]);
                    // 在这里可以将药品 ID 和数量进行处理，例如存储到集合中或者进行其他操作
                    System.out.println("药品ID: " + id + ", 数量: " + quantity);
                    Medicines select = medicinesDao.selectById(id);
                    select.setUseCount(quantity);
                    medicines.add(select);
                } else {
                    // 处理药品信息格式错误的情况
                    System.out.println("药品信息格式错误: " + medicineInfo);
                }

            }
        }
        patient.setMedicines(medicines);
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
        selectById.setModifyTime(LocalDate.now().toString());
        StringBuilder sb = new StringBuilder();
        for (Medicines medicine : patient.getMedicines()) {
            Prescription prescription = new Prescription();
            prescription.setPatientID(selectById.getPatientID());
            prescription.setDoctorID(selectById.getDoctorID());
            prescription.setPrescriptionTime(LocalDate.now().toString());
            prescription.setQuantity(medicine.getUseCount());
            prescription.setMedicineID(medicine.getMedicineID());
            prescription.setCostPrice(medicine.getCostPrice());
            prescription.setPrice(medicine.getPrice());
            prescriptionDao.insert(prescription);
            sb.append(medicine.getMedicineID()).append(":").append(medicine.getName()).append(":").append(medicine.getUseCount()).append(";");
        }
        String medicinesString = sb.toString();
        if (selectById.getMedicine() != null) {
            for (String medicineInfo : selectById.getMedicine().split(";")) {
                if (!medicinesString.contains(medicineInfo)) {
                    String[] parts = medicineInfo.split(":");
                    Medicines medicines = new Medicines();
                    medicines.setMedicineID(Integer.valueOf(parts[0]));
                    medicines.setUseCount(-Integer.valueOf(parts[2]));
                    medicinesService.updateCount(medicines);
                }
            }
        }
        selectById.setMedicine(medicinesString);
        patientDao.updateById(selectById);
        return new Result(selectById, Code.UPDATE_OK, "修改成功");
    }

    @Override
    public Result getPatient() {
//        获取医生id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userID = (Integer) map.get("userID");
        Doctor doctor = doctorDao.selectByUserID(userID);
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctorID", doctor.getDoctorID());
        List<Patient> list = patientDao.selectList(queryWrapper);
        if (list != null) {
            setDetail(list);
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
            List<Integer> userIds = new ArrayList<>();
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
        setDetail(list);
        return new Result(list, Code.GET_OK, "查询成功");
    }

    public void setDetail(List<Patient> list) {
        for (Patient p : list) {
            User user = userDao.selectById(p.getUserID());
            p.setUser(user);
            setMedicines(p);
            Doctor doctor = doctorDao.selectById(p.getDoctorID());
            if (doctor != null) {
                User selectById = userDao.selectById(doctor.getUserID());
                doctor.setUser(selectById);
            }
            p.setDoctor(doctor);
        }
    }

    @Override
    public Result getme() {
        QueryWrapper<Patient> wrapper = new QueryWrapper<>();
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userID = (Integer) map.get("userID");
        wrapper.eq("userID", userID);
        List<Patient> list = patientDao.selectList(wrapper);
        if (list != null) {
            setDetail(list);
        }
        return new Result(list, Code.GET_OK, "查询成功");
    }

    @Override
    public Result getAllNotFinish() {
        QueryWrapper<Patient> wrapper = new QueryWrapper<>();
        wrapper.eq("finish", 0);
        List<Patient> list = patientDao.selectList(wrapper);
        extracted(list);
        return new Result(list, Code.GET_OK, "查询成功");
    }

    @Override
    public Result finish(Integer id) {
        Patient patient = patientDao.selectById(id);
        patient.setModifyTime(LocalDate.now().toString());
        patient.setFinish(1);
        patientDao.updateById(patient);
        return new Result(patient, Code.UPDATE_OK, "修改成功");
    }

    @Override
    public Result getMouthProfit() {
        QueryWrapper<Patient> wrapper = new QueryWrapper<>();
        wrapper.like("finish", 1);
        LocalDate now = LocalDate.now();
        String currentYearMonth = now.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        wrapper.like("modify_time", currentYearMonth);
        List<Patient> list = patientDao.selectList(wrapper);
        BigDecimal totalCost = list.stream()
                .map(Patient::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new Result(totalCost, Code.GET_OK, "查询成功");
    }

    @Override
    public Result getProfit() {
        QueryWrapper<Patient> wrapper = new QueryWrapper<>();
        wrapper.like("finish", 1);
        LocalDate now = LocalDate.now();
        List<Patient> list = patientDao.selectList(wrapper);
        BigDecimal totalCost = list.stream()
                .map(Patient::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new Result(totalCost, Code.GET_OK, "查询成功");
    }

    private void extracted(List<Patient> list) {
        if (list != null) {
            for (Patient patient : list) {
                User user = userDao.selectById(patient.getUserID());
                patient.setUser(user);
                Doctor doctor = doctorDao.selectById(patient.getDoctorID());
                if (doctor != null) {
                    User selectById = userDao.selectById(doctor.getUserID());
                    doctor.setUser(selectById);
                }
                setMedicines(patient);
            }
        }
    }
}
