package com.psx.blog.service;

import com.psx.blog.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SecurityUserService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //登录的时候会把username传递到其中
        Admin admin = adminService.findAdminByUsername(username);
        if(admin==null){
            //登陆失败
            return null;
        }
        UserDetails user = new User(username,admin.getPassword(),new ArrayList<>());
        return user;
    }
}
