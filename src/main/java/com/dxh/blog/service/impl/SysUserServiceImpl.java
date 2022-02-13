package com.dxh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dxh.blog.dao.mapper.SysUserMapper;
import com.dxh.blog.dao.pojo.SysUser;
import com.dxh.blog.service.LoginService;
import com.dxh.blog.service.SysUserService;
import com.dxh.blog.vo.ErrorCode;
import com.dxh.blog.vo.LoginUserVo;
import com.dxh.blog.vo.Result;
import com.dxh.blog.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;
    @Lazy
    @Autowired
    LoginService loginService;

    @Override
    public SysUser findUser(String account, String pwd) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,pwd);
        queryWrapper.select(SysUser::getId,SysUser::getAccount,
                SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
        SysUser sysUser=sysUserMapper.selectOne(queryWrapper);

        return sysUser;
    }

    @Override
    public SysUser findUserById(Long id) {

        return sysUserMapper.selectById(id);
    }

    @Override
    public Result findUserByToken(String token) {
        /**
         * 1.token合法性校验
         *  是否为空 解析是否成功 redis是否存在
         * 2.如果校验失败。返回错误
         * 3.如果成功 返回对应的结果 LoginUserVo
         */
        SysUser sysUser=loginService.checkToken(token);
        if (sysUser==null)
        {
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }

        LoginUserVo loginUserVo=new LoginUserVo();

        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());

        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getAccount,account);
        lambdaQueryWrapper.last("limit 1");
        return sysUserMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public void save(SysUser sysUser) {
        // 用户id会自动生成
        //分布式id 雪花算法
        //mybatis-plus框架
        sysUserMapper.insert(sysUser);
    }

    @Override
    public UserVo findUserVoById(Long authorId) {
        SysUser sysUser=sysUserMapper.selectById(authorId);
        if (sysUser==null){
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("浪潮之巅");
        }
        UserVo userVo=new UserVo();
        userVo.setId(sysUser.getId());
        userVo.setNickname(sysUser.getNickname());
        userVo.setAvatar(sysUser.getAvatar());
        return userVo;
    }
}
