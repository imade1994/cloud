
package com.tj.cloud.redis.config;

import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import javax.annotation.Resource;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/5
 * @Description:
 * @version:1.0
 */
@ConditionalOnProperty(prefix = "cloud.redis", name = "mode", havingValue = "cluster")
public class RedisClusterConfig {

	private static final Logger log = LoggerFactory.getLogger(RedisClusterConfig.class);

	@Resource
	RedisProperties redisProperties;

	@Bean("lettuceConnectionFactory")
	LettuceConnectionFactory lettuceConnectionFactory() {
		// Map<String,Object> propertiesMap = new HashMap<>();
		// propertiesMap.put(REDIS_CLUSTER_NODES,redisModeProperties.getCluster().getNodes());
		// propertiesMap.put(REDIS_CLUSTER_MAX_REDIRECT,redisModeProperties.getCluster().getMaxRedirects());
		// propertiesMap.put(REDIS_CLUSTER_TIMEOUT,redisModeProperties.getTimeout());
		// RedisClusterConfiguration redisClusterConfiguration = new
		// RedisClusterConfiguration(new
		// MapPropertySource(REDIS_CLUSTER_CONFIGURATION,propertiesMap));
		log.info("init lettuce pool cluster redis !");
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
		redisClusterConfiguration.setUsername(redisProperties.getUsername());
		redisClusterConfiguration.setPassword(redisProperties.getPassword());
		redisClusterConfiguration.setClusterNodes(redisClusterConfiguration.getClusterNodes());
		redisClusterConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
		ClusterTopologyRefreshOptions refreshOptions = ClusterTopologyRefreshOptions.builder().enablePeriodicRefresh()
				.enableAllAdaptiveRefreshTriggers()
				.refreshPeriod(redisProperties.getLettuce().getCluster().getRefresh().getPeriod()).build();

		ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
				.topologyRefreshOptions(refreshOptions).build();
		LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
				.clientOptions(clusterClientOptions)
				.shutdownTimeout(redisProperties.getLettuce().getShutdownTimeout()).build();
		return new LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration);
	}

	@Bean
	RedisTemplate<Object, Object> redisTemplate(
			@Qualifier("lettuceConnectionFactory") LettuceConnectionFactory lettuceConnectionFactory) {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		redisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);
		return redisTemplate;
	}

	@Bean
	public StringRedisTemplate stringRedisTemplate(
			@Qualifier("lettuceConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
		return new StringRedisTemplate(redisConnectionFactory);
	}

}
