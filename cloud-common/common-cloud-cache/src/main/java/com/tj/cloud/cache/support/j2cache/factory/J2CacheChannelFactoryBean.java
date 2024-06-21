package com.tj.cloud.cache.support.j2cache.factory;

import com.tj.cloud.cache.support.j2cache.constant.J2CacheConstant;
import com.tj.cloud.core.utils.PropertyUtil;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2CacheBuilder;
import net.oschina.j2cache.J2CacheConfig;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

/**
 * * @Author codingMan_tj * @Date 2024/3/25 16:15 * @version v1.0.0 * @desc j2cache 配置工厂
 **/
public class J2CacheChannelFactoryBean implements FactoryBean<CacheChannel> {

	@Resource
	private StandardEnvironment environment;

	@Resource
	private ApplicationContext applicationContext;

	/**
	 * redis序列化模板
	 */
	private RedisTemplate<?, ?> redisTemplate;

	@Override
	public CacheChannel getObject() {
		J2CacheConfig config = new J2CacheConfig();
		config.setSerialization(environment.getProperty("j2cache.serialization"));
		config.setBroadcast(PropertyUtil.getRelaxStringValue(environment, "j2cache.broadcast.provider_class"));
		config.setL1CacheName(PropertyUtil.getRelaxStringValue(environment, "j2cache.L1.provider_class"));
		config.setL2CacheName(PropertyUtil.getRelaxStringValue(environment, "j2cache.L2.provider_class"));
		config.setSyncTtlToRedis(
				!BooleanUtils.toBoolean(PropertyUtil.getRelaxStringValue(environment, "j2cache.sync_ttl_to_redis")));
		config.setDefaultCacheNullObject(BooleanUtils
				.toBoolean(PropertyUtil.getRelaxStringValue(environment, "j2cache.default_cache_null_object")));
		putProperties(J2CacheConstant.SPRING_APPLICATION_CONTEXT, applicationContext, config.getL1CacheProperties(),
				config.getL2CacheProperties(), config.getBroadcastProperties());
		if (Objects.nonNull(redisTemplate)) {
			putProperties(J2CacheConstant.REDIS_TEMPLATE, redisTemplate, config.getL1CacheProperties(),
					config.getL2CacheProperties(), config.getBroadcastProperties());
		}

		Set<String> filterPropertyNames = new HashSet<>();
		for (PropertySource<?> propertySource : environment.getPropertySources()) {
			if (propertySource instanceof EnumerablePropertySource) {
				for (String propertyName : ((EnumerablePropertySource<?>) propertySource).getPropertyNames()) {
					if (StringUtils.startsWith(propertyName, J2CacheConstant.L1_PREFIX)
							&& filterPropertyNames.add(propertyName)) {
						config.getL1CacheProperties().putIfAbsent(
								replaceJ2CachePrefix(propertyName, J2CacheConstant.L1_PREFIX),
								environment.getProperty(propertyName));
					}
					else if (StringUtils.startsWith(propertyName, J2CacheConstant.L2_PREFIX)
							&& filterPropertyNames.add(propertyName)) {
						config.getL2CacheProperties().putIfAbsent(
								replaceJ2CachePrefix(propertyName, J2CacheConstant.L2_PREFIX),
								environment.getProperty(propertyName));
					}
					else if (StringUtils.startsWith(propertyName, J2CacheConstant.BROADCAST_PREFIX)
							&& filterPropertyNames.add(propertyName)) {
						config.getBroadcastProperties().putIfAbsent(
								replaceJ2CachePrefix(propertyName, J2CacheConstant.BROADCAST_PREFIX),
								environment.getProperty(propertyName));
					}
				}
			}
		}
		config.getBroadcastProperties().putAll(config.getL1CacheProperties());
		config.getBroadcastProperties().putAll(config.getL2CacheProperties());
		return J2CacheBuilder.init(config).getChannel();
	}

	private String replaceJ2CachePrefix(String key, String prefix) {
		return StringUtils.substring(key, prefix.length() + 1).replace('-', '_');
	}

	@Override
	public Class<?> getObjectType() {
		return CacheChannel.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	/**
	 * put value in properties
	 * @param key 键
	 * @param value 值
	 * @param properties 配置
	 */
	private void putProperties(String key, Object value, Properties... properties) {
		for (Properties p : properties) {
			p.put(key, value);
		}
	}

	public void setRedisTemplate(RedisTemplate<?, ?> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

}
