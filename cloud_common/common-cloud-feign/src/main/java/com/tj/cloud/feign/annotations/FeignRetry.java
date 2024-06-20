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
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FeignRetry {
    Backoff backoff() default @Backoff();

    int maxAttempt() default 3;

    Class<? extends Throwable>[] include() default {};
}
