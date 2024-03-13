package com.example.service.impl;

import com.example.controller.Code;
import com.example.controller.Result;
import com.example.domain.Dish;
import com.example.dao.DishDao;
import com.example.service.IDishService;
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
public class DishServiceImpl extends ServiceImpl<DishDao, Dish> implements IDishService {

    @Autowired
    private DishDao dishDao;

    @Override
    public Result add(Dish dish) {
        Map<String, Object> map = ThreadLocalUtil.get();
        dish.setUserID((String) map.get("userID"));
        dish.setCreateTime(LocalDate.now().toString());
        int insert = dishDao.insert(dish);
        if (insert == 0) {
            return new Result(null, Code.SAVE_ERR, "新增失败");
        }
        return new Result(dish, Code.SAVE_OK, "新增成功");
    }

    @Override
    public Result update(Dish dish) {
        dish.setModifyTime(LocalDate.now().toString());
        int update = dishDao.updateById(dish);
        if (update == 0) {
            return new Result(null, Code.UPDATE_ERR, "修改失败");
        }
        return new Result(dish, Code.UPDATE_OK, "修改成功");
    }
}
