package com.example.controller;


import com.example.domain.Appointments;
import com.example.domain.Doctor;
import com.example.domain.Medicines;
import com.example.service.IAppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 戴金磊
 * @since 2024-03-31
 */
@RestController
@RequestMapping("/appointments")
public class AppointmentsController {
    @Autowired
    private IAppointmentsService appointmentsService;

    @PostMapping("/add")
    public Result add(@RequestBody Appointments appointments){
        return appointmentsService.add(appointments);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Appointments appointments){
        return appointmentsService.update(appointments);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        return appointmentsService.delete(id);
    }

    @GetMapping("/getAll")
    public Result getAll(){
        return appointmentsService.getAll();
    }
}

