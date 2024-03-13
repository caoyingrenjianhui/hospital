package com.example.service;

import com.example.controller.Result;
import com.example.domain.Merchant;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 */
public interface IMerchantService extends IService<Merchant> {

    Result add(Merchant merchant);

    Result deleteById(Integer id);
}
