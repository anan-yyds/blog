package com.psx.blog.utils;

import com.psx.blog.dao.SysUser;

public class UserThreadLocal {

    private UserThreadLocal(){

    }
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();
    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }
    public static SysUser get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }
}
