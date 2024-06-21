package com.tj.cloud.cache.enums;

import com.tj.cloud.cache.config.CloudJ2CacheConfiguration;
import com.tj.cloud.cache.config.CloudMemoryCacheConfiguration;
import com.tj.cloud.cache.config.CloudRedisCacheConfiguration;

/**
 * * @Author codingMan_tj * @Date 2024/3/25 15:39 * @version v1.0.0 * @desc 缓存枚举类
 **/
public enum CloudCacheType {

	/**
	 * 内存缓存
	 */
	MEMORY(CloudMemoryCacheConfiguration.class),

	/**
	 * redis缓存
	 */
	REDIS(CloudRedisCacheConfiguration.class),

	/**
	 * j2cache
	 */
	J2CACHE(CloudJ2CacheConfiguration.class);

	/**
	 * 配置类
	 */
	private final Class<?> configurationClass;

	CloudCacheType(Class<?> configurationClass) {
		this.configurationClass = configurationClass;
	}

	public Class<?> getConfigurationClass() {
		return configurationClass;
	}

}
