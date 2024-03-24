package com.example.controller;


import com.example.domain.Orderitem;
import com.example.service.IOrderitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 * 订单
 */
@RestController
@RequestMapping("/orderitem")
public class OrderitemController {

    @Autowired
    private IOrderitemService orderitemService;

    @PostMapping("/add")
    public Result add(@RequestBody Orderitem orderitem){
        return orderitemService.add(orderitem);
    }

    @PutMapping("/finish/{id}")
    public Result finish(@PathVariable Integer id){
        return orderitemService.finish(id);
    }

    /**
     * 商家接单
     * @param id
     * @return
     */
    @PutMapping("/receive/{id}")
    public Result receive(@PathVariable Integer id){
        return orderitemService.receive(id);
    }

    /**
     * 删除订单
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        return orderitemService.delete(id);
    }
}

