package com.tj.cloud.message.config;

import com.tj.cloud.message.properties.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * * @Author codingMan_tj * @Date 2024/4/9 16:17 * @version v1.0.0 * @desc
 **/
@EnableConfigurationProperties(RedisProperties.class)
public class CloudRedisMessageConfiguration {

	@Bean
	RedisTemplate<Object, Object> redisTemplate() {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
		return redisTemplate;
	}

}
