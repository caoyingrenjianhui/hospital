package com.example.controller;


import com.example.domain.User;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    //    根据id查询
    @GetMapping("/{id}")
    public Result getById(@PathVariable String id) {
        User user = userService.getById(id);
        Integer code = user != null ? Code.GET_OK : Code.GET_ERR;
        String msg = code == Code.GET_OK ? "" : "数据查询失败，请重试";
        return new Result(user, code, msg);
    }

    @PostMapping("/register")
    public Result register(@RequestBody @Validated User user) {
        return userService.register(user);
    }

    @GetMapping("/all")
    public Result getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        return userService.login(user);
    }

    /**
     * 获取用户详细信息
     *
     * @param
     * @return
     */
    @GetMapping("/userInfo")
    public Result userInfo() {
        return userService.userInfo();
    }

    @PutMapping("update")
    public Result update(@RequestBody @Validated User user) {
        return userService.update(user);
    }

    @PatchMapping("/updatePhoto")
    public Result updatePhoto(@RequestParam String photo) {
        return userService.updatePhoto(photo);
    }

    @PutMapping("updatePassword")
    public Result updatePassword(@RequestBody User user){
        return userService.updatePassword(user);
    }
}

