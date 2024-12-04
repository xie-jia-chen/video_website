package com.example.controller;

import com.example.common.Result;
import com.example.entity.Comment;
import com.example.service.CommentService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Comment comment){
        commentService.add(comment);
        return Result.success();
    }
    /**
     * 更新
     */
    @PutMapping("/update")
    public Result update(@RequestBody Comment comment){
        commentService.update(comment);
        return Result.success();
    }
    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        commentService.deleteById(id);
        return Result.success();
    }

    /**
    查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Comment comment){
        List<Comment> list = commentService.selectAll(comment);
        return Result.success(list);
    }
    /**
    分页模糊查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Comment comment,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Comment> pageInfo = commentService.selectPage(comment, pageNum, pageSize);
        return Result.success(pageInfo);
    }
}
