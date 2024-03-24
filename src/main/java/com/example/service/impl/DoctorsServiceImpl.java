package com.example.service.impl;

import com.example.controller.Code;
import com.example.controller.Result;
import com.example.dao.DoctorDao;
import com.example.domain.Doctor;
import com.example.service.IDoctorsService;
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
 * @since 2024-03-24
 */
@Service
public class DoctorsServiceImpl extends ServiceImpl<DoctorDao, Doctor> implements IDoctorsService {
    @Autowired
    private DoctorDao doctorDao;

    @Override
    public Result add(Doctor doctor) {
        doctor.setCreate_time(LocalDate.now().toString());
        Map<String, Object> map = ThreadLocalUtil.get();
        doctor.setUserID((String) map.get("userID"));
        int insert = doctorDao.insert(doctor);
        if (insert != 0) {
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
        doctor.setModify_time(LocalDate.now().toString());
        doctorDao.updateById(doctor);
        return new Result(doctor, Code.UPDATE_OK, "修改成功");
    }

    @Override
    public Result delete(Integer id) {
        Doctor doctor = doctorDao.selectById(id);
        doctor.setModify_time(LocalDate.now().toString());
        doctor.setIsdel(0);
        int i = doctorDao.updateById(doctor);
        if (i != 0) {
            return new Result(null, Code.DELETE_OK, "删除成功");
        } else {
            return new Result(null, Code.DELETE_ERR, "删除失败");
        }
    }

    @Override
    public Result getAll() {
        List<Doctor> list = doctorDao.getAll();
        return new Result(list, Code.GET_OK, "查询成功");
    }
}
