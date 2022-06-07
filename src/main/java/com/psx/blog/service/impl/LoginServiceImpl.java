package com.psx.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.psx.blog.common.R;
import com.psx.blog.dao.SysUser;
import com.psx.blog.service.LoginService;
import com.psx.blog.service.SysUserService;
import com.psx.blog.utils.JWTUtils;
import com.psx.blog.vo.param.LoginParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.transaction.annotation.Transactional;;


import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    private static final String slat = "psx!@#";
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public R login(LoginParam loginParam) {
        //检查参数是否合法
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return R.error("参数错误");
        }
        password = DigestUtils.md5Hex(password+slat);
        //根据用户名和密码去user表查询是否存在
        SysUser sysUser = sysUserService.findUser(account,password);
        //如果不存在，登陆失败
        if(sysUser == null){
            return R.error("用户名或密码错误");
        }
        //如果存在，使用jwt生成token返回给前端
        String token = JWTUtils.createToken(sysUser.getId());
        System.out.println("token="+token);
        //token放入redis当中
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return R.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if(stringObjectMap == null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_"+token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public R logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return R.success(null);
    }

    @Override
    public R register(LoginParam loginParam) {
        String account = loginParam.getAccount();
        String nickname = loginParam.getNickname();
        String password = loginParam.getPassword();
        if(StringUtils.isBlank(account) ||
           StringUtils.isBlank(nickname) ||
           StringUtils.isBlank(password)){
            return R.error("参数错误");
        }
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if(sysUser != null){
            return R.error("账户已经被注册，请重新注册");
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);
        String token = JWTUtils.createToken(sysUser.getId());
        //token放入redis当中
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return R.success(token);
    }
}
