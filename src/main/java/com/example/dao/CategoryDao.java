package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 */
@Mapper
public interface CategoryDao extends BaseMapper<Category> {

    @Select("select * from category")
    List<Category> getAll();

    @Select("select * from category where categoryName = #{categoryName}")
    Category selectByName(String categoryName);

    @Delete("delete from category where categoryID = #{id}")
    int deleteById(Serializable id);

    @Select("select * from category where categoryID = #{categoryID}")
    Category selectById(Integer categoryID);

}
