package com.example.service.impl;

import com.example.controller.Code;
import com.example.controller.Result;
import com.example.domain.Doctor;
import com.example.domain.Medicines;
import com.example.dao.MedicinesDao;
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

    @Override
    public Result add(Medicines medicines) {
        medicines.setCreateTime(LocalDate.now().toString());
        Map<String, Object> map = ThreadLocalUtil.get();
        medicines.setUserID((String) map.get("userID"));
        int insert = medicinesDao.insert(medicines);
        if (insert != 0) {
            return new Result(medicines, Code.SAVE_OK, "新增成功");
        } else {
            return new Result(medicines, Code.SAVE_ERR, "新增失败，请联系管理员");
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

    @Override
    public Result getAll() {
        List<Medicines> list = medicinesDao.getAll();
        return new Result(list, Code.GET_OK, "查询成功");
    }

    @Override
    public Result delete(Integer id) {
        Medicines medicines = medicinesDao.selectById(id);
        medicines.setModifyTime(LocalDate.now().toString());
        medicines.setIsdel(0);
        int i = medicinesDao.updateById(medicines);
        if (i != 0) {
            return new Result(null, Code.DELETE_OK, "删除成功");
        } else {
            return new Result(null, Code.DELETE_ERR, "删除失败");
        }
    }
}
