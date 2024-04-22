package com.example.controller;


import com.example.domain.Patient;
import com.example.domain.Prescription;
import com.example.service.IPrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 戴金磊
 * @since 2024-04-18
 */
@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    private IPrescriptionService prescriptionService;

    @PostMapping("/select")
    public Result select(@RequestBody Prescription prescription){
        return prescriptionService.select(prescription);
    }

    @GetMapping()
    public Result getMyPrescription(){
        return prescriptionService.getMyPrescription();
    }

}

