package com.example.service.impl;

import com.example.domain.Complaint;
import com.example.dao.ComplaintDao;
import com.example.service.IComplaintService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 */
@Service
public class ComplaintServiceImpl extends ServiceImpl<ComplaintDao, Complaint> implements IComplaintService {

}
