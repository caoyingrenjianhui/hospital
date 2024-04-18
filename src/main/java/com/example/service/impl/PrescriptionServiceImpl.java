package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.controller.Code;
import com.example.controller.Result;
import com.example.domain.Patient;
import com.example.domain.Prescription;
import com.example.dao.PrescriptionDao;
import com.example.service.IPrescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Result select(Prescription prescription) {
        QueryWrapper<Prescription> wrapper = new QueryWrapper<>();
        if (prescription.getDoctorID() != null) {
            wrapper.eq("doctorID", prescription.getDoctorID());
        }
        if (prescription.getPatientID() != null) {
            wrapper.eq("patientID", prescription.getPatientID());
        }
        if (prescription.getPrescriptionTime() != null) {
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
        return new Result(list, Code.GET_OK, "查询成功");
    }
}
