package com.dxh.blog.common.aop.cache;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
    //缓存的注解
    long expire() default 1 * 60 *1000;

    String name() default "";
}
