package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 尹洪运
 * @since 2024-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "categoryID", type = IdType.AUTO)
    private Integer categoryID;

    @TableField(value = "categoryName")
    private String categoryName;

    @TableField(value = "userID")
    private String userID;

    private String createTime;

    private String modifyTime;


}
