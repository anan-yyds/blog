package com.psx.blog.controller;

import com.psx.blog.common.R;
import com.psx.blog.service.LoginService;
import com.psx.blog.vo.param.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @PostMapping
    public R login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }
}
