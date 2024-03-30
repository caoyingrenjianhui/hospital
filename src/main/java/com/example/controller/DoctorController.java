package com.example.controller;


import com.example.domain.Doctor;
import com.example.domain.Patient;
import com.example.service.IDoctorsService;
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
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private IDoctorsService doctorsService;

    @PostMapping("/add")
    public Result add(@RequestBody Doctor doctor){
        return doctorsService.add(doctor);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Doctor doctor){
        return doctorsService.update(doctor);
    }

    @GetMapping("/getAll")
    public Result getAll() {
        return doctorsService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        return doctorsService.delete(id);
    }

    @PostMapping("/select")
    public Result select(@RequestBody Doctor doctor){
        return doctorsService.select(doctor);
    }

}

