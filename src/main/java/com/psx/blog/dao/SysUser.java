package com.psx.blog.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class SysUser {
//    @TableId(type = IdType.AUTO)
    private Long id;	
	
    private String account;	
	
    private Integer admin;	
	
    private String avatar;	
	
    private Long createDate;	
	
    private Integer deleted;	
	
    private String email;	
	
    private Long lastLogin;	
	
    private String mobilePhoneNumber;	
	
    private String nickname;	
	
    private String password;	
	
    private String salt;	
	
    private String status;	
}	
	
