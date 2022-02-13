package com.dxh.blog.dao.controller;

import com.dxh.blog.dao.pojo.SysUser;
import com.dxh.blog.utils.UserThreadLocal;
import com.dxh.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        System.out.println("==============");
        SysUser sysUser= UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}