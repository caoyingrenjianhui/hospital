package com.example.controller;


import com.example.domain.Checkups;
import com.example.domain.Patient;
import com.example.service.ICheckupsService;
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
@RequestMapping("/checkups")
public class CheckupsController {

    @Autowired
    private ICheckupsService checkupsService;
    @PostMapping("/select")
    public Result select(@RequestBody Checkups checkups){
        return checkupsService.select(checkups);
    }

    @GetMapping("/getCheckups")
    public Result getCheckups() {
        return checkupsService.getCheckups();
    }

    @PostMapping("/add")
    public Result add(@RequestBody Checkups checkups){
        return checkupsService.add(checkups);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Checkups checkups){
        return checkupsService.update(checkups);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        return checkupsService.delete(id);
    }

    @GetMapping("/getme")
    public Result getme() {
        return checkupsService.getme();
    }

    @GetMapping("/getAll")
    public Result getAll() {
        return checkupsService.getAll();
    }

    @GetMapping("/getUserHeartRatesWithDate/{checkupId}")
    public Result getUserHeartRatesWithDate(@PathVariable Integer checkupId) {
        return checkupsService.getUserHeartRatesWithDate(checkupId);
    }

    @GetMapping("/getUserBloodPressureWithDate/{checkupId}")
    public Result getUserBloodPressureWithDate(@PathVariable Integer checkupId) {
        return checkupsService.getUserBloodPressureWithDate(checkupId);
    }

    @GetMapping("/getMyHeartRatesWithDate")
    public Result getMyHeartRatesWithDate() {
        return checkupsService.getMyHeartRatesWithDate();
    }

    @GetMapping("/getMyBloodPressureWithDate")
    public Result getMyBloodPressureWithDate() {
        return checkupsService.getMyBloodPressureWithDate();
    }

}

