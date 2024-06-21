package com.tj.cloud.core.abs;

/**
 * * @Author codingMan_tj * @Date 2024/3/25 14:19 * @version v1.0.0 * @desc 抽象请求返回接口
 **/
public interface IResult {

	/**
	 * 本地调用是否成功
	 */
	Boolean getIsOk();

	/**
	 * 调用状态码
	 */
	String getCode();

	/**
	 * 调用信息
	 */
	String getMsg();

	/**
	 * 调用出错堆栈信息
	 */
	String getCause();

}
