package com.psx.blog.service;

import com.psx.blog.common.R;
import com.psx.blog.dao.SysUser;
import com.psx.blog.vo.param.LoginParam;


public interface LoginService {
    R login(LoginParam loginParam);

    SysUser checkToken(String token);

    R logout(String token);

    R register(LoginParam loginParam);

}
