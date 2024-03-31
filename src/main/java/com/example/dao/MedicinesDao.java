package com.example.dao;

import com.example.domain.Medicines;
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
 * @since 2024-03-24
 */
@Mapper
public interface MedicinesDao extends BaseMapper<Medicines> {

    @Select("select * from Medicines where isdel=0")
    List<Medicines> getAll();
}
