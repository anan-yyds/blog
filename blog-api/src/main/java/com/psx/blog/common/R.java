package com.psx.blog.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class R<T> {

    private Integer code; //编码：200成功，其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private boolean success;
    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 200;
        r.success=true;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.success=false;
        return r;
    }


}
