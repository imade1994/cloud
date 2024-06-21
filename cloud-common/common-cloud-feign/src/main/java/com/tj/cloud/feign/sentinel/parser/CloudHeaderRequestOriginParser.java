package com.tj.cloud.feign.sentinel.parser;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import javax.servlet.http.HttpServletRequest;

/**
 * * @Author codingMan_tj * @Date 2024/3/26 15:58 * @version v1.0.0 * @desc sentinel
 * 请求头解析判断
 **/
public class CloudHeaderRequestOriginParser implements RequestOriginParser {

	/**
	 * 请求头获取allow
	 */
	private static final String ALLOW = "Allow";

	/**
	 * Parse the origin from given HTTP request.
	 * @param request HTTP request
	 * @return parsed origin
	 */
	@Override
	public String parseOrigin(HttpServletRequest request) {
		return request.getHeader(ALLOW);
	}

}
