package com.example.service.impl;

import com.example.controller.Code;
import com.example.controller.Result;
import com.example.domain.Orderitem;
import com.example.dao.OrderitemDao;
import com.example.service.IOrderitemService;
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
public class OrderitemServiceImpl extends ServiceImpl<OrderitemDao, Orderitem> implements IOrderitemService {

    @Autowired
    private OrderitemDao orderitemDao;

    @Override
    public Result add(Orderitem orderitem) {
        Map<String, Object> map = ThreadLocalUtil.get();
        orderitem.setUserID((String) map.get("userID"));
        orderitem.setCreateTime(LocalDate.now().toString());
        int insert = orderitemDao.insert(orderitem);
        if (insert != 0) {
            return new Result(orderitem, Code.SAVE_OK, "新增成功");
        } else {
            return new Result(orderitem, Code.SAVE_ERR, "新增失败，请联系管理员");
        }
    }

    @Override
    public Result finish(Integer id) {
        Orderitem orderitem = orderitemDao.selectById(id);
        if (orderitem == null) {
            return new Result(null, Code.GET_ERR, "无此订单");
        }
        orderitem.setModifyTime(LocalDate.now().toString());
        int i = orderitemDao.updateById(orderitem);
        if (i == 0) {
            return new Result(orderitem, Code.SAVE_ERR, "新增失败");
        }
        return new Result(orderitem, Code.SAVE_OK, "新增成功");
    }
}
