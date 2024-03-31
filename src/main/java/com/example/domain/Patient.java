package com.example.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
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
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "patientID", type = IdType.AUTO)
    private Integer patientID;

    @TableField(value = "userID")
    private String userID;

    @TableField(exist = false)
    private User user;

    @TableField(value = "doctorID")
    private Integer doctorID;

    @TableField(exist = false)
    private Doctor doctor;

    private String symptoms;

    private String medicine;

    private String createTime;

    private String modifyTime;

    private BigDecimal cost;

    @TableLogic
    private Integer isdel;


}
