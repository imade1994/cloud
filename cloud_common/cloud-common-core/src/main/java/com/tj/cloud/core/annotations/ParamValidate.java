package com.tj.cloud.core.annotations;

import java.lang.annotation.*;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/25 11:58
 * * @version v1.0.0
 * * @desc 参数拦截校验
 **/

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamValidate {

    /**
     *
     *  正则校验 规则 regx
     * */
    String value() default "";
}
