package com.tj.cloud.cache.config;

import com.tj.cloud.cache.support.redis.RedisCache;
import com.tj.cloud.core.abs.ICache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * * @Author codingMan_tj * @Date 2024/3/25 16:22 * @version v1.0.0 * @desc redis 缓存实现
 **/
@Conditional(CloudCacheConditional.class)
public class CloudRedisCacheConfiguration {

	@Resource
	RedisConnectionFactory redisConnectionFactory;

	@Bean
	public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String,Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
		redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
		return redisTemplate;
	}


	@Bean
	public ICache iCache() {
		return new RedisCache(redisTemplate(redisConnectionFactory));
	}


}
