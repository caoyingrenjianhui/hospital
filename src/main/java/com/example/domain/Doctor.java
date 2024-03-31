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
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "doctorID", type = IdType.AUTO)
    private Integer doctorID;

    @TableField(value = "userID")
    private String userID;

    @TableField(exist = false)
    private User user;

    private String department;

    private String specialization;

    private String createTime;

    private String modifyTime;

    @TableLogic
    private Integer isdel;
}
