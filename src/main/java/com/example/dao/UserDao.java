package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
public interface UserDao extends BaseMapper<User> {

    @Select("select * from user")
    List<User> getAllUser();

    @Select("select * from user where userID = #{id}")
    User selectById(Serializable id);

    @Update("update user set username = #{username},password = #{password},email=#{email},phone=#{phone},address=#{address},modify_time = #{modifyTime} where userID = #{userID}")
    void update(User user);

    @Update("update user set photo = #{photo}, modify_time = now() where userID = #{userID}")
    void updatePhoto(String photo, String userID);

    @Update("update user set password = #{password}, modify_time = now() where userID = #{userID}")
    void updatePassword(String password, String userID);
}
