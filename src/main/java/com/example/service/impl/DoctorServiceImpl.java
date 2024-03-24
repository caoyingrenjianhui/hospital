package com.example.service.impl;

import com.example.domain.Doctor;
import com.example.dao.DoctorDao;
import com.example.service.IDoctorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 戴金磊
 * @since 2024-03-24
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorDao, Doctor> implements IDoctorService {

}
