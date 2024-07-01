
package com.tj.cloud.annotation;

import java.lang.annotation.*;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	/**
	 * 描述
	 * @return {String}
	 */
	String value() default "";

	/**
	 * spel 表达式
	 * @return 日志描述
	 */
	String expression() default "";

}
