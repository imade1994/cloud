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

	public static <T> CloudResult<T> ok(String system) {
		return restResult(null, StatusCodeEnum.SUCCESS.getCode(), "请求成功",system);
	}

	public static <T> CloudResult<T> ok(T data,String system) {
		return restResult(data, StatusCodeEnum.SUCCESS.getCode(), "请求成功",system);
	}

	public static <T> CloudResult<T> ok(T data, String msg,String system) {
		return restResult(data, StatusCodeEnum.SUCCESS.getCode(), msg,system);
	}


	public static <T> CloudResult<T> failed(String system) {
		return restResult(null, StatusCodeEnum.SYSTEM_ERROR.getCode(), null,system);
	}

	public static <T> CloudResult<T> failed(String msg,String system) {
		return restResult(null, StatusCodeEnum.SYSTEM_ERROR.getCode(), msg,system);
	}

	public static <T> CloudResult<T> failed(T data,String system) {
		return restResult(data, StatusCodeEnum.SYSTEM_ERROR.getCode(), "请求失败",system);
	}

	public static <T> CloudResult<T> failed(T data, String msg,String system) {
		return restResult(data, StatusCodeEnum.SYSTEM_ERROR.getCode(), msg,system);
	}

	public static <T> CloudResult<T> restResult(T data, int code, String msg,String system) {
		CloudResult<T> apiResult = new CloudResult<>();
		apiResult.setIsOk(code==StatusCodeEnum.SUCCESS.getCode());
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setDesc(msg);
		apiResult.setSystem(system);
		return apiResult;
	}
	public static <T> CloudResult<T> restResult(T data, int code, String msg) {
		CloudResult<T> apiResult = new CloudResult<>();
		apiResult.setIsOk(code==StatusCodeEnum.SUCCESS.getCode());
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setDesc(msg);
		return apiResult;
	}

}
