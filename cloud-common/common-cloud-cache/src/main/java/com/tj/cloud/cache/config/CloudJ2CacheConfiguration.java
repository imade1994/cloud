package com.tj.cloud.cache.config;

import com.tj.cloud.cache.support.j2cache.constant.J2CacheConstant;
import com.tj.cloud.cache.support.j2cache.core.J2CacheImpl;
import com.tj.cloud.cache.support.j2cache.factory.J2CacheChannelFactoryBean;
import com.tj.cloud.cache.support.redis.FastRedisSerializer;
import com.tj.cloud.core.abs.ICache;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * * @Author codingMan_tj * @Date 2024/3/25 15:40 * @version v1.0.0 * @desc j2cache 配置类
 **/
@Conditional(CloudCacheConditional.class)
public class CloudJ2CacheConfiguration {

	private BeanFactory beanFactory;

	private Environment environment;

	public CloudJ2CacheConfiguration(BeanFactory beanFactory, Environment environment) {
		this.beanFactory = beanFactory;
		this.environment = environment;
	}

	@Bean
	public J2CacheChannelFactoryBean cacheChannel() {
		J2CacheChannelFactoryBean j2CacheChannelFactoryBean = new J2CacheChannelFactoryBean();
		if (BooleanUtils.toBoolean(environment.getProperty("j2cache.L2.cache-open"))) {
			j2CacheChannelFactoryBean.setRedisTemplate(createRedisTemplate());
		}
		return j2CacheChannelFactoryBean;
	}

	@SuppressWarnings("unchecked")
	private RedisTemplate createRedisTemplate() {
		final String serialization = environment.getProperty("j2cache.serialization");
		RedisSerializer redisSerializer = null;
		if (ObjectUtils.isEmpty(serialization) || ObjectUtils.nullSafeEquals(serialization, "java")) {
			redisSerializer = new JdkSerializationRedisSerializer();
		}
		else if (ObjectUtils.nullSafeEquals(serialization, "fst")) {
			redisSerializer = new FastRedisSerializer();
		}
		Assert.notNull(redisSerializer, "j2cache only supports java serialization or fst serialization");
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(beanFactory.getBean(RedisConnectionFactory.class));
		redisTemplate.setDefaultSerializer(redisSerializer);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	@Bean
	public ICache iCache() {
		return new J2CacheImpl();
	}

	@Bean(name = J2CacheConstant.HL_REDIS_MESSAGE_CONTAINER_BEAN_ID)
	@ConditionalOnMissingBean(name = J2CacheConstant.HL_REDIS_MESSAGE_CONTAINER_BEAN_ID)
	public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
		RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
		redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
		return redisMessageListenerContainer;
	}

}
