package com.dxh.blog.service;

import com.dxh.blog.dao.pojo.SysUser;
import com.dxh.blog.vo.Result;
import com.dxh.blog.vo.params.LoginParams;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LoginService {

    /**
     * 登录
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);

    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     * @param loginParams
     * @return
     */
    Result register(LoginParams loginParams);
}
