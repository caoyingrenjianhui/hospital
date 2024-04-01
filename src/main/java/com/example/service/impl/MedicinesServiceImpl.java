package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.controller.Code;
import com.example.controller.Result;
import com.example.dao.UserDao;
import com.example.domain.Doctor;
import com.example.domain.Medicines;
import com.example.dao.MedicinesDao;
import com.example.domain.User;
import com.example.service.IMedicinesService;
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
public class MedicinesServiceImpl extends ServiceImpl<MedicinesDao, Medicines> implements IMedicinesService {

    @Autowired
    private MedicinesDao medicinesDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Result add(Medicines medicines) {
        medicines.setCreateTime(LocalDate.now().toString());
        Map<String, Object> map = ThreadLocalUtil.get();
        medicines.setUserID((String) map.get("userID"));
        if (medicinesDao.selectById(medicines.getMedicineID()) != null) {
            return new Result(medicines, Code.SAVE_ERR, "新增失败，已有此ID的药品");
        }
        int insert = medicinesDao.insert(medicines);
        if (insert != 0) {
            return new Result(medicines, Code.SAVE_OK, "新增成功");
        } else {
            return new Result(medicines, Code.SAVE_ERR, "新增失败，请联系管理员");
        }
    }

    @Override
    public Result update(List<Medicines> medicines) {
        for (Medicines medicine : medicines) {
            Medicines selectById = medicinesDao.selectById(medicine.getMedicineID());
            if (selectById != null) {
                medicine.setModifyTime(LocalDate.now().toString());
                Map<String, Object> map = ThreadLocalUtil.get();
                selectById.setUserID((String) map.get("userID"));
                selectById.setCount(selectById.getCount() - medicine.getUseCount());
                selectById.setUseCount(selectById.getUseCount() + medicine.getUseCount());
                medicinesDao.updateById(selectById);
            }
        }
        return new Result(medicines, Code.UPDATE_OK, "修改成功");
    }

    @Override
    public Result getAll() {
        List<Medicines> list = medicinesDao.getAll();
        if (list != null) {
            for (Medicines medicine : list) {
                User user = userDao.selectById(medicine.getUserID());
                medicine.setUser(user);
            }

        }
        return new Result(list, Code.GET_OK, "查询成功");
    }

    @Override
    public Result delete(Integer id) {
        Medicines medicines = medicinesDao.selectById(id);
        medicines.setModifyTime(LocalDate.now().toString());
        int update = medicinesDao.updateById(medicines);
        int i = medicinesDao.deleteById(id);
        if (i != 0) {
            return new Result(null, Code.DELETE_OK, "删除成功");
        } else {
            return new Result(null, Code.DELETE_ERR, "删除失败");
        }
    }

    @Override
    public Result select(Medicines medicines) {
        QueryWrapper<Medicines> wrapper = new QueryWrapper<>();
        if (medicines.getMedicineID() != null) {
            wrapper.eq("medicineID", medicines.getMedicineID());
        }
        if (medicines.getSupplier() != null && !"".equals(medicines.getSupplier())) {
            wrapper.like("supplier", medicines.getSupplier());
        }
        if (medicines.getAddress() != null && !"".equals(medicines.getAddress())) {
            wrapper.like("address", medicines.getAddress());
        }
        if (medicines.getName() != null && !"".equals(medicines.getName())) {
            wrapper.like("name", medicines.getName());
        }
        List<Medicines> list = medicinesDao.selectList(wrapper);
        if (list == null) {
            return new Result(null, Code.GET_ERR, "查询不到数据");
        }
        return new Result(list, Code.GET_OK, "查询成功");
    }

    @Override
    public Result updateCount(Medicines medicines) {
        Medicines selectById = medicinesDao.selectById(medicines.getMedicineID());
        selectById.setCount(selectById.getCount() - medicines.getUseCount());
        selectById.setUseCount(selectById.getUseCount() + medicines.getUseCount());
        int i = medicinesDao.updateById(selectById);
        if (i != 0) {
            return new Result(selectById, Code.UPDATE_OK, "修改药品数量成功");
        } else {
            return new Result(selectById, Code.UPDATE_ERR, "修改药品数量失败");
        }
    }

    @Override
    public Result update(Medicines medicines) {
        Medicines selectById = medicinesDao.selectById(medicines.getMedicineID());
        if (selectById == null) {
            return new Result(null, Code.UPDATE_ERR, "无此药品");
        }
        medicines.setModifyTime(LocalDate.now().toString());
        Map<String, Object> map = ThreadLocalUtil.get();
        medicines.setUserID((String) map.get("userID"));
        medicinesDao.updateById(medicines);
        return new Result(medicines, Code.UPDATE_OK, "修改成功");
    }
}
