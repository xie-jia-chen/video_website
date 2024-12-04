package com.example.service;


import cn.hutool.core.date.DateUtil;
import com.example.entity.Comment;
import com.example.mapper.CommentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Resource
    private CommentMapper commentMapper;

    /**
     * 新增
     */
    public void add(Comment comment) {
        comment.setTime(DateUtil.now());
        commentMapper.insert(comment);
    }

    /**
     * 更新
     */
    public void update(Comment comment) {
        commentMapper.updateById(comment);
    }
    /**
     * 删除
     */
    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
    }
    /**
     * 查询所有
     */
    public List<Comment> selectAll(Comment comment){
        return commentMapper.selectAll(comment);
    }

    /**
     * 分页查询
     * @param pageNum 当前页码
     * @param pageSize 每页个数
     * @return 分页对象 包含数据和分页参数 total
     */
    public PageInfo<Comment> selectPage(Comment comment,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Comment> list = commentMapper.selectAll(comment);
        return PageInfo.of(list);
    }


}
