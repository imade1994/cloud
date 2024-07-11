
package com.tj.cloud.feign.constant;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/24
 * @Description:
 * @version:1.0
 */
public interface GatewayConstant {

	/**
	 * 全局缓存，在缓存名称上加上该前缀表示该缓存不区分租户，比如:
	 * <p/>
	 * {@code @Cacheable(value = CacheConstants.GLOBALLY+CacheConstants.MENU_DETAILS, key = "#roleId  + '_menu'", unless = "#result == null")}
	 */
	String GLOBALLY = "gl:";

	/**
	 * spring boot admin 事件key
	 */
	String EVENT_KEY = GLOBALLY + "event_key";

	/**
	 * 路由存放
	 */
	String ROUTE_KEY = GLOBALLY + "gateway_route_key";

	/**
	 * 内存reload 时间
	 */
	String ROUTE_JVM_RELOAD_TOPIC = "gateway_jvm_route_reload_topic";

	/**
	 * redis 重新加载 路由信息
	 */
	String ROUTE_REDIS_RELOAD_TOPIC = "upms_redis_route_reload_topic";

	/**
	 * 参数缓存
	 */
	String PARAMS_DETAILS = "params_details";

	/**
	 * 租户缓存 (不区分租户)
	 */
	String TENANT_DETAILS = GLOBALLY + "tenant_details";

	/**
	 * 客户端配置缓存
	 */
	String CLIENT_FLAG = "client_config_flag";

	/**
	 * header 中版本信息
	 */
	String SERVICE_VERSION = "VERSION";

	/**
	 * 滑块验证码
	 */
	String IMAGE_CODE_TYPE = "blockPuzzle";

	/**
	 * 验证码开关
	 */
	String CAPTCHA_FLAG = "captcha_flag";

}
