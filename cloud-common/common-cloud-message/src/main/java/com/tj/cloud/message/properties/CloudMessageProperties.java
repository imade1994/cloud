package com.tj.cloud.message.properties;

import com.tj.cloud.message.enums.CloudMessageType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * * @Author codingMan_tj * @Date 2024/4/9 15:40 * @version v1.0.0 * @desc
 **/
@ConfigurationProperties(prefix = "cloud.message")
@Data
public class CloudMessageProperties {

	private static final long serialVersionUID = 1L;

	/**
	 * cache type
	 */
	private CloudMessageType type;

	/**
	 * whether to allow for null values
	 */
	private Boolean allowNullValues;

}
