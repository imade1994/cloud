
package com.tj.cloud.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@Getter
@RequiredArgsConstructor
public enum LogTypeEnum {

	/**
	 * 正常日志类型
	 */
	NORMAL("0", "正常日志"),

	/**
	 * 错误日志类型
	 */
	ERROR("9", "错误日志");

	/**
	 * 类型
	 */
	private final String type;

	/**
	 * 描述
	 */
	private final String description;

}
