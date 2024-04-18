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


}
