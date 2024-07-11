package com.tj.cloud.feign.support;

import com.tj.cloud.feign.constant.GatewayConstant;
import com.tj.cloud.feign.vo.RouteDefinitionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/5
 * @Description:
 * @version:1.0
 */
@Component
public class RedisRouteDefinitionWriter implements RouteDefinitionRepository {

    private static final Logger log = LoggerFactory.getLogger(RedisRouteDefinitionWriter.class);



    private final RedisTemplate<Object,Object> redisTemplate;


    public RedisRouteDefinitionWriter(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            RouteDefinitionVo vo = new RouteDefinitionVo();
            BeanUtils.copyProperties(r, vo);
            log.info("保存路由信息{}", vo);
            redisTemplate.opsForHash().put(GatewayConstant.ROUTE_KEY, r.getId(), vo);
            redisTemplate.convertAndSend(GatewayConstant.ROUTE_JVM_RELOAD_TOPIC, "新增路由信息,网关缓存更新");
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        routeId.subscribe(id -> {
            log.info("删除路由信息{}", id);
            //redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.opsForHash().delete(GatewayConstant.ROUTE_KEY, id);
        });
        redisTemplate.convertAndSend(GatewayConstant.ROUTE_JVM_RELOAD_TOPIC, "删除路由信息,网关缓存更新");
        return Mono.empty();
    }

    /**
     * 动态路由入口
     * <p>
     * 1. 先从内存中获取 2. 为空加载Redis中数据 3. 更新内存
     *
     * @return
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinitionVo> routeList = RouteCacheHolder.getRouteList();
        log.debug("内存 中路由定义条数： {}， {}", routeList.size(), routeList);
        List<Object> cacheList = redisTemplate.opsForHash().values(GatewayConstant.ROUTE_KEY);
        List<RouteDefinitionVo> routeDefinitionVoList = new ArrayList<>();
        cacheList.forEach(s ->{
            routeDefinitionVoList.add((RouteDefinitionVo) s);
        });
        log.debug("redis 中路由定义条数： {}， {}", routeDefinitionVoList.size(), routeDefinitionVoList);
        if (routeList.size() == routeDefinitionVoList.size() && new HashSet<>(routeList).containsAll(routeDefinitionVoList)) {
            log.debug("使用内存中的路由： {}， {}", routeDefinitionVoList.size(), routeDefinitionVoList);
            return Flux.fromIterable(routeList);
        } else {
            log.debug("更新内存中的路由： {}， {}", routeDefinitionVoList.size(), routeDefinitionVoList);
            RouteCacheHolder.removeRouteList();
            RouteCacheHolder.setRouteList(routeDefinitionVoList);
            return Flux.fromIterable(routeDefinitionVoList);
        }

    }


}
