package com.example.controller;


import com.example.domain.Category;
import com.example.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 * 菜品分类
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping("/add")
    public Result add(@RequestBody Category category) {
        return categoryService.add(category);
    }

    @GetMapping("/getAll")
    public Result getAll() {
        return categoryService.getAll();
    }

    @DeleteMapping("/delete/{categoryID}")
    public Result delete(@PathVariable Integer categoryID) {
        return categoryService.delete(categoryID);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Category category) {
        return categoryService.update(category);
    }

}

