package com.dxh.blog.handler;

import com.dxh.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice  //对加了controller注解的方法进行拦截处理
public class AllExceptionHandler {

    //进行异常处理 处理 Exception.class
    @ExceptionHandler(Exception.class)
    @ResponseBody //返回JSON数据
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(999,"系统异常");
    }

}
