package com.example.service.impl;

import com.example.domain.Prescription;
import com.example.dao.PrescriptionDao;
import com.example.service.IPrescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 戴金磊
 * @since 2024-04-18
 */
@Service
public class PrescriptionServiceImpl extends ServiceImpl<PrescriptionDao, Prescription> implements IPrescriptionService {

}
