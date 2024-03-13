package com.example.service.impl;

import com.example.controller.Code;
import com.example.controller.Result;
import com.example.domain.Merchant;
import com.example.dao.MerchantDao;
import com.example.service.IMerchantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantDao, Merchant> implements IMerchantService {

    @Autowired
    private MerchantDao merchantDao;

    @Override
    public Result add(Merchant merchant) {
        merchantDao.insert(merchant);
        return new Result(merchant, Code.SAVE_OK, "新增成功");
    }

    @Override
    public Result deleteById(Integer id) {
        int deleteById = merchantDao.deleteById(id);
        if (deleteById == 0) {
            return new Result(null, Code.DELETE_ERR, "删除失败");
        }
        return new Result(null, Code.DELETE_OK, "删除成功");
    }
}
