package com.dxh.blog.dao.controller;

import com.dxh.blog.service.LoginService;
import com.dxh.blog.vo.Result;
import com.dxh.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping
    public Result login(@RequestBody LoginParams loginParams){
        return loginService.login(loginParams);
    }
}
