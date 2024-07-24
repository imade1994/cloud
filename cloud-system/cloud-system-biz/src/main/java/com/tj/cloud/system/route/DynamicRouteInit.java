package com.tj.cloud.system.route;

import com.alibaba.fastjson2.JSONObject;
import com.tj.cloud.core.constant.CommonConstant;
import com.tj.cloud.system.service.ISysRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/16
 * @Description:
 * @version:1.0
 */
@Component
public class DynamicRouteInit  {

    private static final Logger log = LoggerFactory.getLogger(DynamicRouteInit.class);

    @Resource
    private RedisTemplate<Object,Object> redisTemplate;
    @Resource
    private ISysRouteService sysRouteService;

    @Async
    @Order
    @EventListener({WebServerInitializedEvent.class, DynamicRouteInitEvent.class})
    public void initRoute() {
        redisTemplate.delete(CommonConstant.ROUTE_KEY);
        log.info("开始初始化网关路由");
        sysRouteService.list().forEach(route -> {
            redisTemplate.opsForHash().put(CommonConstant.ROUTE_KEY, route.getId(), JSONObject.from(route));
        });

        // 通知网关重置路由
        redisTemplate.convertAndSend(CommonConstant.ROUTE_JVM_RELOAD_TOPIC, "路由信息,网关缓存更新");
        log.debug("初始化网关路由结束 ");
    }

    /**
     * redis 监听配置,监听 gateway_redis_route_reload_topic,重新加载Redis
     *
     * @param redisConnectionFactory redis 配置
     * @return
     */
    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener((message, bytes) -> {
            log.warn("接收到重新Redis 重新加载路由事件");
            initRoute();
        }, new ChannelTopic(CommonConstant.ROUTE_REDIS_RELOAD_TOPIC));
        return container;
    }
}
