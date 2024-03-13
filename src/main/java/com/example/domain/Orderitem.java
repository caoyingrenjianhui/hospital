package com.example.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @author 尹洪运
 * @since 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Orderitem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "orderID", type = IdType.AUTO)
    private Integer orderID;

    @TableField(value = "userID")
    private String userID;

    private Integer merchantID;

    private BigDecimal totalPrice;

    private String status;

    private String createTime;

    private String modifyTime;

    private String payType;

    @TableLogic
    private Integer isdel;


}
