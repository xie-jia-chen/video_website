package com.example.mapper;

import com.example.entity.Category;

import java.util.List;

public interface CategoryMapper {


    void insert(Category category);

    void updateById(Category category);

    void deleteById(Integer id);

    List<Category> selectAll(Category category);


}
