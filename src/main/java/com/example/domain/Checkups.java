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
 * @since 2024-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Checkups implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "checkup_id", type = IdType.AUTO)
    private Integer checkupId;

    @TableField(value = "userID")
    private Integer userID;

    @TableField(value = "doctorID")
    private Integer doctorID;

    private String checkupDate;

    private Integer bloodPressure;

    private Integer heartRate;

    private BigDecimal height;

    private BigDecimal weight;

    private Integer lungCapacity;

    private String otherParameters;

    @TableLogic
    private Integer isdel;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private Doctor doctor;
}
