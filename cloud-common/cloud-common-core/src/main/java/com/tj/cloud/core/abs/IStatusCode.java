package com.tj.cloud.core.abs;

/**
 * * @Author codingMan_tj * @Date 2024/3/25 13:57 * @version v1.0.0 * @desc 抽象请求结果返回方法
 **/
public interface IStatusCode {

	/**
	 * 状态码
	 * @return 请求状态码
	 */
	int getCode();

	/**
	 * 异常信息
	 * @return 请求描述信息 ok 或者异常信息
	 */
	String getDesc();

	/**
	 * 系统编码
	 * @return 请求来源系统名称
	 */
	String getSystem();

}
