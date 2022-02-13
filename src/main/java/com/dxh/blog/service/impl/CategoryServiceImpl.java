package com.dxh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.dxh.blog.dao.mapper.CategoryMapper;
import com.dxh.blog.dao.pojo.Category;
import com.dxh.blog.service.CategoryService;
import com.dxh.blog.vo.CategoryVo;
import com.dxh.blog.vo.Result;
import io.netty.util.concurrent.SucceededFuture;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        System.out.println("66666666666666666");
        Category category=categoryMapper.selectById(categoryId);
        CategoryVo categoryVo=new CategoryVo();
        System.out.println(category+"=================");
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    @Override
    public Result findAll() {
        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(categories));
    }

    /**
     * 查找分类下对应的文章
     * @param id
     * @return
     */
    @Override
    public Result categoryDetailById(Long id) {
        Category category=categoryMapper.selectById(id);
        return Result.success(copy(category));
    }

    private List<CategoryVo> copyList(List<Category> categories) {
        List<CategoryVo> categoryVoList= new ArrayList<>();
        for (Category c: categories) {
            categoryVoList.add(copy(c));
        }
        return categoryVoList;
    }

    private CategoryVo copy(Category c) {
        CategoryVo categoryVo=new CategoryVo();
        BeanUtils.copyProperties(c,categoryVo);
        return categoryVo;
    }
}
