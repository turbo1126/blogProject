package com.dxh.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dxh.blog.dao.pojo.SysUser;
import com.dxh.blog.vo.Result;
import com.dxh.blog.vo.UserVo;

public interface SysUserService {


    SysUser findUser(String account, String pwd) ;

    SysUser findUserById(Long id);

    /*
    根据token 查询用户信息
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查找用户
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**
     * 返回userVo
     * @param authorId
     * @return
     */
    UserVo findUserVoById(Long authorId);
}
