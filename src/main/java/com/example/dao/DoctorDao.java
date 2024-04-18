package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.Doctor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 戴金磊
 * @since 2024-03-24
 */
@Mapper
public interface DoctorDao extends BaseMapper<Doctor> {

    @Select("select * from doctor where isdel=0")
    List<Doctor> getAll();

    @Select("select * from doctor where userID = #{userID} and isdel=0")
    Doctor selectByUserID(Integer userID);
}
