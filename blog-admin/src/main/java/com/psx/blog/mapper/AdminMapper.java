package com.psx.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psx.blog.pojo.Admin;
import com.psx.blog.pojo.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select * from ms_permission where id in (select permission_id from ms_admin_permission where admin_id = #{adminId})")
   List<Permission> findPermissionByAdminId(Long id);
}
