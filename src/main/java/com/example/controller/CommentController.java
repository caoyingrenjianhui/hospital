package com.example.controller;


import com.example.domain.Comment;
import com.example.service.ICommentService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @PostMapping("/add")
    public Result add(@RequestBody Comment comment) {
        return commentService.add(comment);
    }

    @GetMapping
    public Result selectByUserID(){
        return commentService.selectBuUserID();
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        return commentService.deleteById(id);
    }
}

