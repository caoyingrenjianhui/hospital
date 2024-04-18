package com.example.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;

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
 * @author 戴金磊
 * @since 2024-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Checkups implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "checkup_id", type = IdType.AUTO)
    private Integer checkupId;

    private Integer userID;

    private Integer doctorID;

    private LocalDate checkupDate;

    private Integer bloodPressure;

    private Integer heartRate;

    private BigDecimal height;

    private BigDecimal weight;

    private Integer lungCapacity;

    private String otherParameters;

    @TableField(exist = false)
    private User user;
}
