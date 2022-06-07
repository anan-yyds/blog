package com.psx.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.psx.blog.common.R;
import com.psx.blog.dao.SysUser;
import com.psx.blog.vo.CommentVo;
import com.psx.blog.vo.UserVo;

import java.util.List;

public interface SysUserService extends IService<SysUser> {

    UserVo findUserVoById(Long id);
    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    R findUserByToken(String token);

    SysUser findUserByAccount(String account);


}
