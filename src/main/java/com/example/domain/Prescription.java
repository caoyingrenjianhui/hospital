package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
public class Prescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "doctorID")
    private Integer doctorID;

    private String prescriptionTime;

    private Integer quantity;

    @TableField(value = "patientID")
    private Integer patientID;

    @TableField(value = "medicineID")
    private Integer medicineID;

    @TableField(exist = false)
    private Patient patient;

    @TableField(exist = false)
    private Medicines medicine;

    @TableField(value = "patientID")
    private String patientName;

    @TableField(value = "medicineID")
    private String medicineName;

    @TableLogic
    private Integer isdel;

}
