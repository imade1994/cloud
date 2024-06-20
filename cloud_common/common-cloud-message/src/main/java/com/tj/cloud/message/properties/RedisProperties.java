package com.tj.cloud.message.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * * @Author codingMan_tj
 * * @Date 2024/4/9 16:20
 * * @version v1.0.0
 * * @desc
 **/
@ConfigurationProperties(prefix = "cloud.message.redis")
public class RedisProperties {

    private String queueName;
}
