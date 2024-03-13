package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "userID", type = IdType.ASSIGN_ID)
    private String userID;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String address;

    private String createTime;

    private String modifyTime;

    private Integer userType;

    @TableLogic
    private Integer isdel;

    private String photo;

    @TableField(exist = false)
    private String oldPassword;

    @TableField(exist = false)
    private String rePassword;

    @TableField(exist = false)
    private String userLat;//精度

    @TableField(exist = false)
    private String userLng;//维度

    @TableField(exist = false)
    private String maxDistance;//最大距离

}
