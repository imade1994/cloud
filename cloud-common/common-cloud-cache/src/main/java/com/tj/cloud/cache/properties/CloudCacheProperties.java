package com.tj.cloud.cache.properties;

import com.tj.cloud.cache.enums.CloudCacheType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * * @Author codingMan_tj * @Date 2024/3/25 15:38 * @version v1.0.0 * @desc 缓存配置类
 **/
@Data
@ConfigurationProperties(prefix = "cloud.cache")
public class CloudCacheProperties implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * cache type
	 */
	private CloudCacheType type;

	/**
	 * whether to allow for null values
	 */
	private Boolean allowNullValues;

}
