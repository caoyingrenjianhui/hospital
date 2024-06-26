package com.example.dao;

import com.example.domain.Prescription;
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
public interface PrescriptionDao extends BaseMapper<Prescription> {

    @Select("select * from prescription where doctorID = #{doctorID} and isdel=0")
    List<Prescription> selectByDoctorID(Integer doctorID);
}
