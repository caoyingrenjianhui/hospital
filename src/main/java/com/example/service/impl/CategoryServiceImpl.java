package com.example.service.impl;

import com.example.controller.Code;
import com.example.controller.Result;
import com.example.dao.CategoryDao;
import com.example.domain.Category;
import com.example.service.ICategoryService;
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
 * @author 尹洪运
 * @since 2024-02-29
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements ICategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Result add(Category category) {
//        检查是否有相同的类型
        Category selectByName = categoryDao.selectByName(category.getCategoryName());
        if (selectByName != null) {
            return new Result(selectByName, Code.SAVE_ERR, "已有相同记录");
        }
        category.setCreateTime(LocalDate.now().toString());
        Map<String, Object> map = ThreadLocalUtil.get();
        category.setUserID((String) map.get("userID"));
        int insert = categoryDao.insert(category);
        if (insert != 0) {
            return new Result(category, Code.SAVE_OK, "新增成功");
        } else {
            return new Result(category, Code.SAVE_ERR, "新增失败，请联系管理员");
        }

    }

    @Override
    public Result getAll() {
        List<Category> list = categoryDao.getAll();
        return new Result(list, Code.GET_OK, "查询成功");
    }

    @Override
    public Result delete(Integer categoryID) {
        int deleteById = categoryDao.deleteById(categoryID);
        if (deleteById != 0) {
            return new Result(null, Code.DELETE_OK, "删除成功");
        } else {
            return new Result(null, Code.DELETE_ERR, "删除失败");
        }
    }

    @Override
    public Result update(Category category) {
        Category selectById = categoryDao.selectById(category.getCategoryID());
        if (selectById == null) {
            return new Result(null, Code.UPDATE_ERR, "无此分类");
        }
        selectById.setModifyTime(LocalDate.now().toString());
        categoryDao.updateById(selectById);
        return new Result(selectById, Code.UPDATE_OK, "修改成功");
    }


}
