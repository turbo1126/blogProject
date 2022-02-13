package com.dxh.blog.dao.controller;

import com.dxh.blog.service.LoginService;
import com.dxh.blog.vo.Result;
import com.dxh.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParams loginParams) {
        //sso单点登录 后期如果把登录注册功能提出去
        System.out.println("6666666666666666");
        return loginService.register(loginParams);
    }
}
