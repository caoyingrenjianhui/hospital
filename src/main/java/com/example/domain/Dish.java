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
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dishID", type = IdType.AUTO)
    private Integer dishID;

    private String dishName;

    private BigDecimal price;

    private Integer categoryID;

    private Integer merchantID;

    @TableLogic
    private Integer isdel;

    private String photo;

    @TableField(value = "userID")
    private String userID;

    private String createTime;

    private String modifyTime;
}
