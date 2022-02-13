package com.dxh.blog.service;

import com.dxh.blog.vo.CategoryVo;
import com.dxh.blog.vo.Result;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result categoryDetailById(Long id);
}
