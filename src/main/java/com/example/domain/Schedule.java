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
 * @since 2024-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "scheduleID", type = IdType.AUTO)
    private Integer scheduleID;

    @TableField(value = "doctorID")
    private Integer doctorID;

    /**
     * 排班日期
     */
    private String shiftDate;

    /**
     * 班次类型
     */
    private String shiftType;

    private String createTime;

    private String modifyTime;

    @TableLogic
    private Integer isdel;

    private Integer ishandle;
}
