package com.tj.cloud.security.annotations;

import java.lang.annotation.*;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/29 13:54
 * * @version v1.0.0
 * * @desc
 **/
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inner {
    /**
     * 是否AOP统一处理
     * @return false, true
     */
    boolean value() default true;

    /**
     * 需要特殊判空的字段(预留)
     * @return {}
     */
    String[] field() default {};


}
