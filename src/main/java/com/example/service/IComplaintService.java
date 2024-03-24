package com.example.service;

import com.example.controller.Result;
import com.example.domain.Complaint;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 */
public interface IComplaintService extends IService<Complaint> {

    Result add(Complaint complaint);

    Result handle(Integer id);

    Result delete(Integer id);
}
