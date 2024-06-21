package com.tj.cloud.core.http;

import com.tj.cloud.core.enums.StatusCodeEnum;
import lombok.Data;

/**
 * * @Author codingMan_tj * @Date 2024/3/26 15:11 * @version v1.0.0 * @desc 封装结果集
 **/
@Data
public class CloudResult<T> extends BaseResult {

	private static final long serialVersionUID = 1L;

	/**
	 * 返回数据结果
	 */
	private T data;

	public static <T> CloudResult<T> ok() {
		return restResult(null, StatusCodeEnum.SUCCESS.getCode(), null);
	}

	public static <T> CloudResult<T> ok(T data) {
		return restResult(data, StatusCodeEnum.SUCCESS.getCode(), null);
	}

	public static <T> CloudResult<T> ok(T data, String msg) {
		return restResult(data, StatusCodeEnum.SUCCESS.getCode(), msg);
	}

	public static <T> CloudResult<T> failed() {
		return restResult(null, StatusCodeEnum.SYSTEM_ERROR.getCode(), null);
	}

	public static <T> CloudResult<T> failed(String msg) {
		return restResult(null, StatusCodeEnum.SYSTEM_ERROR.getCode(), msg);
	}

	public static <T> CloudResult<T> failed(T data) {
		return restResult(data, StatusCodeEnum.SYSTEM_ERROR.getCode(), null);
	}

	public static <T> CloudResult<T> failed(T data, String msg) {
		return restResult(data, StatusCodeEnum.SYSTEM_ERROR.getCode(), msg);
	}

	public static <T> CloudResult<T> restResult(T data, int code, String msg) {
		CloudResult<T> apiResult = new CloudResult<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setDesc(msg);
		return apiResult;
	}

}
