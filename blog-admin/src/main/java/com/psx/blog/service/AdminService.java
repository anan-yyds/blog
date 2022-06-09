package com.psx.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.psx.blog.mapper.AdminMapper;
import com.psx.blog.pojo.Admin;
import com.psx.blog.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public Admin findAdminByUsername(String username){
        LambdaQueryWrapper<Admin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Admin::getUsername,username);
        lqw.last("limit 1");
        Admin admin = adminMapper.selectOne(lqw);
        return admin;
    }

    public List<Permission> findPermissionsByAdminId(Long id) {
        return adminMapper.findPermissionByAdminId(id);
    }
}
