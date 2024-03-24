package com.example.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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

    private Integer use_count;

    private String address;

    private String supplier;

    private String userID;

    private String create_time;

    private String modify_time;

    private BigDecimal price;


}
