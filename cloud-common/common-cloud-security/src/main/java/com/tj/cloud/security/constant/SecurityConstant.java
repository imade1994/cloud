package com.tj.cloud.security.constant;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 14:25 * @version v1.0.0 * @desc
 **/
public interface SecurityConstant {

	/**
	 * 角色前缀
	 */
	String ROLE = "ROLE_";

	/**
	 * 项目的license
	 */
	String PROJECT_LICENSE = "https://cloud.com";

	/**
	 * 登陆用户缓存 前缀
	 *
	 */
	String SECURITY_CACHE_USER_DETAILS = "security:cache:userDetails";

	/** 用户名未找到 */
	String USERNAME_NOT_FOUND = "username_not_found";

	/** 错误凭证 */
	String BAD_CREDENTIALS = "bad_credentials";

	/** 用户被锁 */
	String USER_LOCKED = "user_locked";

	/** 用户禁用 */
	String USER_DISABLE = "user_disable";

	/** 用户过期 */
	String USER_EXPIRED = "user_expired";

	/** 证书过期 */
	String CREDENTIALS_EXPIRED = "credentials_expired";

	/** scope 为空异常 */
	String SCOPE_IS_EMPTY = "scope_is_empty";

	/**
	 * 令牌不存在
	 */
	String TOKEN_MISSING = "token_missing";

	/** 未知的登录异常 */
	String UN_KNOW_LOGIN_ERROR = "un_know_login_error";

	/**
	 * 不合法的Token
	 */
	String INVALID_BEARER_TOKEN = "invalid_bearer_token";

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
	 * 手机号登录
	 */
	String APP = "app";

}
