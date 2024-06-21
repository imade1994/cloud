package com.tj.cloud.core.enums;

import com.tj.cloud.core.abs.IStatusCode;

/**
 * * @Author codingMan_tj * @Date 2024/3/26 13:27 * @version v1.0.0 * @desc 异常枚举类
 **/
public enum StatusCodeEnum implements IStatusCode {

	/**
	 * 成功
	 */
	SUCCESS(200, "成功"),
	/**
	 * 系统异常
	 */
	SYSTEM_ERROR(1001, "系统异常"),
	/**
	 * 访问超时
	 */
	TIMEOUT(1002, "访问超时"),
	/**
	 * 参数校验不通过
	 */
	PARAM_ILLEGAL(1003, "参数校验不通过"),
	/**
	 * 不支持的方法
	 */
	NOT_SUPPORT(1004, "不支持的方法"),

	/**
	 * 远程调用服务失败
	 */
	REMOTE_ERROR(1005, "远程调用服务失败"),
	/**
	 * 流水号已存在
	 */
	SERIAL_NUMBER_EXIST(1006, "流水号已存在"),
	/**
	 * 流水号不存在
	 */
	SERIAL_NUMBER_NOT_EXIST(1007, "流水号不存在");

	/**
	 * 编码
	 */
	private int code;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 系统
	 */
	private String system;

	StatusCodeEnum(int code, String description, String system) {
		this.code = code;
		this.desc = description;
		this.system = system;
	}

	StatusCodeEnum(int code, String description) {
		this.code = code;
		this.desc = description;
		this.system = "default";
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public String getSystem() {
		return system;
	}

}
