package com.tj.cloud.feign.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/26 15:53
 * * @version v1.0.0
 * * @desc
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Backoff {

    long delay() default 1000L;

    long maxDelay() default 0L;

    double multiplier() default 0.0D;
}
