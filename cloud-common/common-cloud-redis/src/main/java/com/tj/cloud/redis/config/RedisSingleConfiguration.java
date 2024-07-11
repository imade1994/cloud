
package com.tj.cloud.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/5
 * @Description:
 * @version:1.0
 */
@ConditionalOnProperty(prefix = "cloud.redis", name = "mode", havingValue = "single")
public class RedisSingleConfiguration {

	private static final Logger log = LoggerFactory.getLogger(RedisSingleConfiguration.class);


	/*@Bean("jedisConnectionFactory")
	JedisConnectionFactory jedisConnectionFactory() {
		log.info("init jedis pool single redis !");
		RedisStandaloneConfiguration singleConfiguration = new RedisStandaloneConfiguration();
		singleConfiguration.setHostName(redisModeProperties.getHost());
		singleConfiguration.setPort(redisModeProperties.getPort());
		singleConfiguration.setDatabase(redisModeProperties.getDatabase());
		singleConfiguration.setPassword(redisModeProperties.getPassword());
		return new JedisConnectionFactory(singleConfiguration);
	}*/

	@Bean
	public RedisTemplate<Object, Object> redisTemplate(
			@Qualifier("jedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setKeySerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		template.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

	@Bean
	public StringRedisTemplate stringRedisTemplate(
			@Qualifier("jedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
		return new StringRedisTemplate(redisConnectionFactory);
	}

}
