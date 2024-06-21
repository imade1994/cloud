package com.tj.cloud.message.enums;

import com.tj.cloud.message.config.*;

/**
 * * @Author codingMan_tj * @Date 2024/4/9 15:44 * @version v1.0.0 * @desc
 **/
public enum CloudMessageType {

	/**
	 * rabbitmq
	 */
	RABBITMQ(CloudRabbitmqMessageConfiguration.class),

	/**
	 * redis 消息
	 */
	REDIS(CloudRedisMessageConfiguration.class),

	/**
	 * kafka 消息
	 */
	KAFKA(CloudKafkaMessageConfiguration.class),

	/**
	 * pulsar 消息
	 */
	PULSAR(CloudPulsarMessageConfiguration.class),

	/**
	 * rocket 消息
	 */
	ROCKETMQ(CloudRocketmqMessageConfiguration.class);

	/**
	 * 配置类
	 */
	private final Class<?> configurationClass;

	CloudMessageType(Class<?> configurationClass) {
		this.configurationClass = configurationClass;
	}

	public Class<?> getConfigurationClass() {
		return configurationClass;
	}

}
