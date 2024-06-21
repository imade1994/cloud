package com.tj.cloud.core.annotations;

import java.lang.annotation.*;

/**
 * * @Author codingMan_tj * @Date 2024/3/25 11:55 * @version v1.0.0 * @desc 异常处理标签
 **/
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CatchError {

	/**
	 * 返回信息 默认为空
	 */
	String value() default "";

	/**
	 * 目前全部都是 RestController 请确认是否需要写入 response 形式 默认不写入
	 */
	boolean write2response() default false;

}
