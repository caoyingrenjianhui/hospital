package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;

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
 * @author 戴金磊
 * @since 2024-03-24
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

    private String create_time;

    private String modify_time;

    private Integer user_type;

    @TableLogic
    private Integer isdel;

    private Integer identity;

    private String photo;

    @TableField(exist = false)
    private String oldPassword;

    @TableField(exist = false)
    private String rePassword;
}
