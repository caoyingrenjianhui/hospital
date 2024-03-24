package com.example.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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

    @TableId(value = "patientID", type = IdType.ASSIGN_ID)
    private Integer patientID;

    private String userID;

    private Integer doctorID;

    private String symptoms;

    private String medicine;

    private String create_time;

    private String modify_time;

    private BigDecimal cost;

    @TableLogic
    private Integer isdel;


}
