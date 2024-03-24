package com.example.controller;


import com.example.domain.Complaint;
import com.example.service.IComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 * 投诉
 */
@RestController
@RequestMapping("/complaint")
public class ComplaintController {

    @Autowired
    private IComplaintService complaintService;

    @PostMapping("/add")
    public Result add(@RequestBody Complaint complaint){
        return complaintService.add(complaint);
    }

    /**
     * 处理投诉
     * @param id
     * @return
     */
    @PutMapping("/handle/{id}")
    public Result handle(@PathVariable Integer id){
        return complaintService.handle(id);
    }

    /**
     * 删除投诉
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        return complaintService.delete(id);
    }
}

