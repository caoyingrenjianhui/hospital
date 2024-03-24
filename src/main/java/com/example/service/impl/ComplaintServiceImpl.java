package com.example.service.impl;

import com.example.controller.Code;
import com.example.controller.Result;
import com.example.domain.Complaint;
import com.example.dao.ComplaintDao;
import com.example.service.IComplaintService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 */
@Service
public class ComplaintServiceImpl extends ServiceImpl<ComplaintDao, Complaint> implements IComplaintService {

    @Autowired
    private ComplaintDao complaintDao;

    @Override
    public Result add(Complaint complaint) {
        Map<String, Object> map = ThreadLocalUtil.get();
        complaint.setUserID((String) map.get("userID"));
        complaint.setCreateTime(LocalDate.now().toString());
        int insert = complaintDao.insert(complaint);
        if (insert == 0) {
            return new Result(null, Code.SAVE_ERR, "新增失败");
        }
        return new Result(complaint, Code.SAVE_OK, "新增成功");
    }

    @Override
    public Result handle(Integer id) {
        Complaint complaint = complaintDao.selectById(id);
        Map<String, Object> map = ThreadLocalUtil.get();
        complaint.setHandleID((String) map.get("userID"));
        int i = complaintDao.updateById(complaint);
        if (i == 0) {
            return new Result(null, Code.UPDATE_ERR, "修改失败");
        }
        return new Result(complaint, Code.UPDATE_OK, "修改成功");
    }

    @Override
    public Result delete(Integer id) {
        Complaint complaint = complaintDao.selectById(id);
        complaint.setModifyTime(LocalDate.now().toString());
        complaint.setIsdel(0);
        int i = complaintDao.updateById(complaint);
        if (i != 0) {
            return new Result(null, Code.DELETE_OK, "删除成功");
        } else {
            return new Result(null, Code.DELETE_ERR, "删除失败");
        }
    }
}
