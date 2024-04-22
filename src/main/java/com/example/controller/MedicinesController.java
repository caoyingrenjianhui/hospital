package com.example.controller;


import com.example.domain.Medicines;
import com.example.service.IMedicinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

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

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        return medicinesService.delete(id);
    }

    @PostMapping("/select")
    public Result select(@RequestBody Medicines medicines){
        return medicinesService.select(medicines);
    }

    @PostMapping("/updateCount")
    public Result updateCount(@RequestBody Medicines medicines){
        return medicinesService.updateCount(medicines);
    }

    @PutMapping("/updateMedicine")
    public Result updateMedicine(@RequestBody List<Medicines> medicines){
        return medicinesService.update(medicines);
    }

    //    上传头像
    @PostMapping("/upload")
    public String up(@RequestParam("medicineID") String medicineID, MultipartFile photo, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //  获取图片的原始名称
        System.out.println("mingcheng:" + photo.getOriginalFilename());
//        获取文件类型
        System.out.println("leixing:" + photo.getContentType());
//        获取在网络上运行的路径
        String path = request.getServletContext().getRealPath("/upload/");
        System.out.println("path" + path);
        saveFile(photo, path);
//        保存到数据库
        String pic = "http://localhost:9080/upload/" + photo.getOriginalFilename();
        System.out.println("pic" + pic);
//        页面路径
        String referer = request.getHeader("referer");
        if (medicineID != null && medicineID != "") {
            Medicines medicines = new Medicines();
            medicines.setMedicineID(Integer.valueOf(medicineID));
            medicines.setPhoto(pic);
            medicinesService.upphoto(medicines);
//            不跳转页面
            response.sendRedirect(referer);
            return ("上传成功");
        }
        response.sendRedirect(referer);
        return ("上传失败");
    }

    //    上传头像
    private void saveFile(MultipartFile photo, String path) throws IOException {
//        存储目录是否存在，不存在就创建
        File dir = new File(path);
        if (!dir.exists()) {
//            创建目录
            dir.mkdir();
        }
        File file = new File(path + photo.getOriginalFilename());
        photo.transferTo(file);
        System.out.println(file);
    }

}

