package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.domain.Merchant;
import com.example.domain.User;
import com.example.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 */
@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private IMerchantService merchantService;

    @PostMapping("/add")
    public Result add(@RequestBody Merchant merchant) {
        return merchantService.add(merchant);
    }

    @PostMapping("/searchNearbyMerchants")
    public Result searchNearbyMerchants(@RequestBody User user) {

        // 构造查询条件
        QueryWrapper<Merchant> queryWrapper = Wrappers.<Merchant>query();
        queryWrapper.apply("6371 * acos(cos(radians(?)) * cos(radians(latitude)) * cos(radians(longitude) - radians(?)) + sin(radians(?)) * sin(radians(latitude)) < ?",
                user.getUserLat(), user.getUserLng(), user.getUserLat(), user.getMaxDistance());

        // 调用 MyBatis-Plus 的 list 方法查询符合条件的商家信息
        List<Merchant> nearbyMerchants = merchantService.list(queryWrapper);
        if (nearbyMerchants.size() == 0) {
            return new Result(null, Code.GET_ERR, "未查询到相关商家");
        }
        return new Result(nearbyMerchants, Code.GET_OK, "查询成功");
    }

    @DeleteMapping("/delete")
    public Result delete(@PathVariable Integer id){
        return merchantService.deleteById(id);
    }
}

