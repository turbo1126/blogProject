package com.dxh.blog.utils;

import com.dxh.blog.dao.pojo.SysUser;

public class UserThreadLocal {

    private UserThreadLocal(){}

    private static final ThreadLocal<SysUser> LOCAL=new InheritableThreadLocal<>();

    public static void put(SysUser sysUser){ LOCAL.set(sysUser); }
    public static SysUser get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }
}
