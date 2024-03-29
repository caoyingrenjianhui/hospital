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
public class Medicines implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "medicineID", type = IdType.ASSIGN_ID)
    private Integer medicineID;

    private String name;

    private Integer count;

    private Integer useCount;

    private String address;

    private String supplier;

    private String userID;

    @TableField(exist = false)
    private User user;

    private String createTime;

    private String modifyTime;

    private BigDecimal price;

    @TableLogic
    private Integer isdel;


}
