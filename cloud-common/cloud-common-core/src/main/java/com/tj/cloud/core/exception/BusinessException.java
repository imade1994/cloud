package com.tj.cloud.core.exception;

import com.tj.cloud.core.abs.IStatusCode;
import com.tj.cloud.core.enums.StatusCodeEnum;

/**
 * * @Author codingMan_tj * @Date 2024/3/26 13:25 * @version v1.0.0 * @desc 基础异常
 **/
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IStatusCode statusCode = StatusCodeEnum.SYSTEM_ERROR;

	public BusinessException(String msg) {
		super(msg);
	}

	public BusinessException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public BusinessException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * 格式化的异常消息，
	 * @param msgTemplate 如：账户[%s]对应密码不正确
	 * @param params 对应 msgTemplate %s 入参,按顺序填入
	 */
	public BusinessException(String msgTemplate, String... params) {
		super(String.format(msgTemplate, params));
	}

	/**
	 * 自定义异常MSG
	 * @param msg
	 * @param errorCode
	 */
	public BusinessException(String msg, IStatusCode errorCode) {
		super(msg);
		this.statusCode = errorCode;
	}

	/**
	 * 使用异常码的异常描述
	 * @param errorCode
	 */
	public BusinessException(IStatusCode errorCode) {
		super(errorCode.getDesc());
		this.statusCode = errorCode;
	}

	public BusinessException(IStatusCode errorCode, Throwable throwable) {
		super(String.format("%s,可能的原因为：%s", errorCode.getDesc(), throwable.getMessage()), throwable);
		this.statusCode = errorCode;
	}

	/**
	 * 异常包装后抛出
	 * @param msg
	 * @param errorCode
	 * @param throwable
	 */
	public BusinessException(String msg, IStatusCode errorCode, Throwable throwable) {
		super(String.format("%s[%s],可能的原因为：%s", errorCode.getDesc(), msg, throwable.getMessage()), throwable);
		this.statusCode = errorCode;
	}

	public Integer getStatusCode() {
		if (statusCode == null)
			return 0;
		return statusCode.getCode();
	}

	public IStatusCode getIStatusCode() {
		return statusCode;
	}

	public void setStatusCode(IStatusCode statusCode) {
		this.statusCode = statusCode;
	}

}
