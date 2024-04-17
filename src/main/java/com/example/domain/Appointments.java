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
 * @since 2024-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Appointments implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "appointmentID", type = IdType.AUTO)
    private Integer appointmentID;

    @TableField(value = "doctorID")
    private Integer doctorID;

    @TableField(value = "userID")
    private Integer userID;

    private String appointmentDate;

    private String appointmentNotes;

    @TableLogic
    private Integer isdel;

    /**
     * 班次类型
     */
    private String shiftType;

}
