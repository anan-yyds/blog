package com.psx.blog.controller;

import com.psx.blog.common.R;
import com.psx.blog.utils.QiniuUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping
    public R upload(@RequestParam("image")MultipartFile file){

        String originalFilename = file.getOriginalFilename();

        String suffix = StringUtils.substringAfterLast(originalFilename, ".");

        String fileName = UUID.randomUUID().toString() + "." + suffix;

        boolean upload = qiniuUtils.upload(file, fileName);
        if(upload){
            return R.success(QiniuUtils.url + "/" + fileName);
        }
        return R.error("上传失败");

    }

}
