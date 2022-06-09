package com.psx.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.psx.blog.common.PageResult;
import com.psx.blog.common.R;
import com.psx.blog.mapper.PermissionMapper;
import com.psx.blog.model.params.PageParam;
import com.psx.blog.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    public R listPermission(PageParam pageParam) {

        Page<Permission> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Permission> lqw = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(pageParam.getQueryString())){
            lqw.eq(Permission::getName,pageParam.getQueryString());
        }
        Page<Permission> permissionPage = permissionMapper.selectPage(page, lqw);
        PageResult<Permission> pageResult = new PageResult<>();
        pageResult.setList(permissionPage.getRecords());
        pageResult.setTotal(permissionPage.getTotal());
        return R.success(pageResult);
    }

    public R add(Permission permission) {
        permissionMapper.insert(permission);
        return R.success(null);
    }

    public R update(Permission permission) {
        permissionMapper.updateById(permission);
        return R.success(null);
    }

    public R delete(Long id) {
        permissionMapper.deleteById(id);
        return R.success(null);
    }
}
