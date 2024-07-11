
package com.tj.cloud.feign.config;

import cn.hutool.core.collection.CollectionUtil;
import com.tj.cloud.core.utils.SpringContextHolder;
import com.tj.cloud.feign.constant.GatewayConstant;
import com.tj.cloud.feign.exception.RouteCheckException;
import com.tj.cloud.feign.support.DynamicRouteHealthIndicator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.config.PropertiesRouteDefinitionLocator;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Collections;
import java.util.List;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/5
 * @Description:
 * @version:1.0
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class DynamicRouteAutoConfiguration {

	/**
	 * 配置文件设置为空 redis 加载为准
	 * @return
	 */
	@Bean
	public PropertiesRouteDefinitionLocator propertiesRouteDefinitionLocator() {
		return new PropertiesRouteDefinitionLocator(new GatewayProperties());
	}

	/**
	 * redis 监听配置
	 * @param redisConnectionFactory redis 配置
	 * @return
	 */
	@Bean
	public RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory);
		container.addMessageListener((message, bytes) -> {
			log.warn("接收到重新JVM 重新加载路由事件");
			RouteCacheHolder.removeRouteList();
			// 发送刷新路由事件
			SpringContextHolder.publishEvent(new RefreshRoutesEvent(this));
		}, new ChannelTopic(GatewayConstant.ROUTE_JVM_RELOAD_TOPIC));
		return container;
	}

	/**
	 * 动态路由监控检查
	 * @param redisTemplate redis
	 * @return
	 */
	@Bean
	public DynamicRouteHealthIndicator healthIndicator(RedisTemplate<Object,Object> redisTemplate) {
		List<Object> list = redisTemplate.opsForHash().values(GatewayConstant.ROUTE_KEY);
		if (CollectionUtil.isEmpty(list)) {
			throw new RouteCheckException("路由信息未初始化，网关启动失败 请先启动 sys模块");
		}

		return new DynamicRouteHealthIndicator(redisTemplate);
	}

}
