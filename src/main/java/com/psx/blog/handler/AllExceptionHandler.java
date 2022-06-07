package com.psx.blog.handler;

import com.psx.blog.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AllExceptionHandler {

    //进行异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回json数据
    public R<String> doException(Exception e){
        e.printStackTrace();
        return R.error("系统异常");
    }
}
