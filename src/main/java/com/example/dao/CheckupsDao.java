package com.example.dao;

import com.example.domain.Checkups;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 戴金磊
 * @since 2024-04-18
 */
@Mapper
public interface CheckupsDao extends BaseMapper<Checkups> {

    @Select("select * from checkups where isdel=0")
    List<Checkups> getAll();

    @Select("select * from checkups where userID = #{userID} and isdel=0")
    List<Checkups> selectListByuserID(Integer userID);
}
