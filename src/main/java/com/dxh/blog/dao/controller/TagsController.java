package com.dxh.blog.dao.controller;

import com.dxh.blog.service.TagService;
import com.dxh.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tags")
public class TagsController {

    @Autowired
    TagService tagService;


    @GetMapping("hot")   //无参请求，用get
    public Result hot(){
        int limit=6;
        Result hot = tagService.hot(limit);
        return hot;
    }

    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }

    //查询所有标签
    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAll();
    }

    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }
}
