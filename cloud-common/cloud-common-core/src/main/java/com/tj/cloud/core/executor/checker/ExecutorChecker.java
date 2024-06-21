package com.tj.cloud.core.executor.checker;

/**
 * * @Author codingMan_tj * @Date 2024/3/25 14:05 * @version v1.0.0 * @desc 校验器抽象类
 **/
public interface ExecutorChecker {

	/**
	 * 校验器的key 默认是类名
	 * @return 校验器的key
	 */
	String getKey();

	/**
	 * 校验器的名字
	 * @return 校验器的名字
	 */
	String getName();

	/**
	 * 校验执行器
	 * @return 校验执行器
	 */
	boolean check(String executorKey);

}
