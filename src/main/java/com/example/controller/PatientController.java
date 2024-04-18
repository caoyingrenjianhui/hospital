package com.example.controller;


import com.example.domain.Medicines;
import com.example.domain.Patient;
import com.example.service.IPatientService;
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
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private IPatientService patientService;

    @PostMapping("/add")
    public Result add(@RequestBody Patient patient){
        return patientService.add(patient);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Patient patient){
        return patientService.update(patient);
    }

    @GetMapping("/getAll")
    public Result getAll() {
        return patientService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        return patientService.delete(id);
    }

    @GetMapping("/getPatient")
    public Result getPatient() {
        return patientService.getPatient();
    }

    @PostMapping("/select")
    public Result select(@RequestBody Patient patient){
        return patientService.select(patient);
    }

    @GetMapping("/getme")
    public Result getme() {
        return patientService.getme();
    }
}

