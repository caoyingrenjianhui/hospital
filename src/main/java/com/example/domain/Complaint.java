package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class Complaint implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "complaintID", type = IdType.AUTO)
    private Integer complaintID;

    private String userID;

    private Integer merchantID;

    private String content;

    private String createTime;

    private String modifyTime;

    private String status;

    private String handleID;

    @TableLogic
    private Integer isdel;


}
