package com.psx.blog.controller;

import com.psx.blog.common.R;
import com.psx.blog.model.params.PageParam;
import com.psx.blog.pojo.Permission;
import com.psx.blog.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private PermissionService permissionService;
    @PostMapping("permission/permissionList")
    public R listPermission(@RequestBody PageParam pageParam){
        /**
         * 要的数据，管理台 表的所有字段 Permission
         */
        return permissionService.listPermission(pageParam);
    }

    @PostMapping("permission/add")
    public R add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public R update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public R delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }


}
