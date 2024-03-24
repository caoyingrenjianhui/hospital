package com.example.dao;

import com.example.domain.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 */
@Mapper
public interface CommentDao extends BaseMapper<Comment> {

    @Select("select * from comment where userID = #{userID}")
    List<Comment> selectBuUserID(String userID);
}
