package com.tj.cloud.core.annotations;

import java.lang.annotation.*;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/25 11:51
 * * @version v1.0.0
 * * @desc 是否显示枚举类
 **/

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UnDisplay {



    /**
     * 默认不展示  值为false 需要展示
     *  */
    boolean value() default true;
}
