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
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public R register(@RequestBody LoginParam loginParam){
        return loginService.register(loginParam);
    }
}
