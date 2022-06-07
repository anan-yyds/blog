package com.psx.blog.controller;


import com.psx.blog.common.R;
import com.psx.blog.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@Slf4j
public class UsersController {


    @Autowired
    private SysUserService sysUserService;

    @GetMapping("currentUser")
    public R currentUser(@RequestHeader("Authorization") String token){
        log.info("token={}",token);
        return sysUserService.findUserByToken(token);
    }
}
