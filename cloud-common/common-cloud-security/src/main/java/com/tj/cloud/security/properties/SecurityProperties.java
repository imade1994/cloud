package com.tj.cloud.security.properties;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 10:54 * @version v1.0.0 * @desc
 **/
public class SecurityProperties {

	/**
	 * 忽略xss 的地址 逗号分隔
	 */
	private String xssIgnores = "";

	/**
	 * 忽略跨域访问 的地址 逗号分隔
	 */
	private String csrfIgnores = "127.0.0.1";

	/**
	 * 忽略鉴权 的地址 逗号分隔
	 */
	private String authIgnores = "/login.*";

	/**
	 * 最大会话数 -1：表示会话不受任何限制 0：限制除登陆用户最后一次登陆会话，其它会话都失效 0：限制用户下最大会话数
	 */
	private Integer maximumSessions = -1;

}
