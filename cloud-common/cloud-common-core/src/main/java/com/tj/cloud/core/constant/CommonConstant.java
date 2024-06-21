package com.tj.cloud.core.constant;

/**
 * * @Author codingMan_tj * @Date 2024/3/25 13:37 * @version v1.0.0 * @desc 常量
 **/
public interface CommonConstant {

	/**
	 * 成功标记
	 */
	Integer SUCCESS = 0;

	/**
	 * 失败标记
	 */
	Integer FAIL = 1;

	String FROM = "from";

	String FROM_IN = "Y";

	String UTF8 = "UTF-8";

	String CONTENT_TYPE = "content-type";

	/**
	 * 默认登录URL
	 */
	String OAUTH_TOKEN_URL = "/oauth2/token";

	/**
	 * grant_type
	 */
	String REFRESH_TOKEN = "refresh_token";

	/**
	 * password 模式
	 */
	String PASSWORD = "password";

	/**
	 * 手机号登录
	 */
	String MOBILE = "mobile";

	/**
	 * {bcrypt} 加密的特征码
	 */
	String BCRYPT = "{bcrypt}";

	/**
	 * {noop} 加密的特征码
	 */
	String NOOP = "{noop}";

	/**
	 * 用户名
	 */
	String USERNAME = "username";

	/**
	 * 用户信息
	 */
	String DETAILS_USER = "user_info";

	/**
	 * 用户ID
	 */
	String DETAILS_USER_ID = "user_id";

	/**
	 * 协议字段
	 */
	String DETAILS_LICENSE = "license";

	/**
	 * 验证码有效期,默认 60秒
	 */
	long CODE_TIME = 60;

	/**
	 * 验证码长度
	 */
	String CODE_SIZE = "6";

	/**
	 * 客户端模式
	 */
	String CLIENT_CREDENTIALS = "client_credentials";

	/**
	 * 客户端ID
	 */
	String CLIENT_ID = "clientId";

	/**
	 * 短信登录 参数名称
	 */
	String SMS_PARAMETER_NAME = "mobile";

	/**
	 * 授权码模式confirm
	 */
	String CUSTOM_CONSENT_PAGE_URI = "/token/confirm_access";

	/**
	 * header 中版本信息
	 */
	String VERSION = "VERSION";

}
