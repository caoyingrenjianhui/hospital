package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.controller.Code;
import com.example.controller.Result;
import com.example.dao.DoctorDao;
import com.example.dao.MedicinesDao;
import com.example.dao.PatientDao;
import com.example.domain.*;
import com.example.dao.PrescriptionDao;
import com.example.service.IPatientService;
import com.example.service.IPrescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 戴金磊
 * @since 2024-04-18
 */
@Service
public class PrescriptionServiceImpl extends ServiceImpl<PrescriptionDao, Prescription> implements IPrescriptionService {

    @Autowired
    private PrescriptionDao prescriptionDao;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private PatientServiceImpl patientService;
    @Autowired
    private MedicinesDao medicinesDao;
    @Autowired
    private PatientDao patientDao;

    @Override
    public Result select(Prescription prescription) {
        QueryWrapper<Prescription> wrapper = new QueryWrapper<>();
        if (prescription.getDoctorID() != null) {
            wrapper.eq("doctorID", prescription.getDoctorID());
        }
        if (prescription.getPatientID() != null) {
            wrapper.eq("patientID", prescription.getPatientID());
        }
        if (prescription.getMedicineID() != null) {
            wrapper.eq("medicineID", prescription.getMedicineID());
        }
        if (prescription.getPrescriptionTime() != null) {
            setRightDate(prescription);
            wrapper.eq("prescription_time", prescription.getPrescriptionTime());
        }
//        if (prescription.getPatientName()!=null){
//            QueryWrapper<Patient> patientQueryWrapper = new QueryWrapper<>();
//            patientQueryWrapper.like("")
//        }
        List<Prescription> list = prescriptionDao.selectList(wrapper);
        if (list.size() == 0) {
            return new Result(null, Code.GET_ERR, "未查询到数据");
        }
        setDetail(list);
        return new Result(list, Code.GET_OK, "查询成功");
    }

    private void setRightDate(Prescription prescription) {
        try {
            // 解析前端传递的日期字符串
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date date = inputFormat.parse(prescription.getPrescriptionTime());
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate localDate = instant.atZone(zoneId).toLocalDate();
            LocalDate nextDay = localDate.plusDays(1);
            Date nextDate = Date.from(nextDay.atStartOfDay(zoneId).toInstant());
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nextDayFormatted = outputFormat.format(nextDate);
            prescription.setPrescriptionTime(nextDayFormatted);
        } catch (Exception e) {
            System.out.println("日期转换失败：" + e.getMessage());
        }
    }

    @Override
    public Result getMyPrescription() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userID = (Integer) map.get("userID");
        Doctor doctor = doctorDao.selectByUserID(userID);
        List<Prescription> list = prescriptionDao.selectByDoctorID(doctor.getDoctorID());
        if (list.size() == 0) {
            return new Result(null, Code.GET_ERR, "未查询到数据");
        }
        setDetail(list);
        return new Result(list, Code.GET_OK, "查询成功");
    }

    @Override
    public Result getProfitWithDate() {
        QueryWrapper<Prescription> wrapper = new QueryWrapper<>();
        wrapper.select("DATE_FORMAT(prescription_time, '%Y-%m') AS month",
                        "SUM((price - cost_Price) * quantity) AS profit")
                .groupBy("DATE_FORMAT(prescription_time, '%Y-%m')");
        List<Map<String, Object>> maps = prescriptionDao.selectMaps(wrapper);
        Map<String, Integer> profit = new HashMap<>();
        for (Map<String, Object> map : maps) {
            String month = (String) map.get("month");
            Integer monthlyProfit = ((BigDecimal) map.get("profit")).intValue(); // 将利润转换为整数
            profit.put(month, monthlyProfit);
        }
        return new Result(profit, Code.GET_OK, "查询成功");
    }

    private void setDetail(List<Prescription> list) {
        for (Prescription prescription : list) {
            Patient patient = patientDao.selectById(prescription.getPatientID());
            patientService.setDetail(Collections.singletonList(patient));
            prescription.setPatientName(patient.getUser().getUsername());
            Medicines medicines = medicinesDao.selectById(prescription.getMedicineID());
            prescription.setMedicineName(medicines.getName());
            prescription.setMedicine(medicines);
        }
    }
}

// 定义用于接收每个月利润结果的类
class ProfitByMonth {
    private String month; // 月份，例如 "2024-04"
    private BigDecimal profit; // 利润

    // Getters and Setters
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
}