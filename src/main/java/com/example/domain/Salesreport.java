package com.example.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Salesreport implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "salesReportID", type = IdType.AUTO)
    private Integer salesReportID;

    private Integer merchantID;

    private BigDecimal totalSales;

    private Integer totalOrders;

    private String reportDate;


}
