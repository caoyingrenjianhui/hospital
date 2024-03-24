package com.example.controller;


import com.example.domain.Medicines;
import com.example.service.IMedicinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 戴金磊
 * @since 2024-03-24
 */
@RestController
@RequestMapping("/medicines")
public class MedicinesController {

    @Autowired
    private IMedicinesService medicinesService;

    @PostMapping("/add")
    public Result add(@RequestBody Medicines medicines){
        return medicinesService.add(medicines);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Medicines medicines){
        return medicinesService.update(medicines);
    }

    @GetMapping("/getAll")
    public Result getAll() {
        return medicinesService.getAll();
    }


}

