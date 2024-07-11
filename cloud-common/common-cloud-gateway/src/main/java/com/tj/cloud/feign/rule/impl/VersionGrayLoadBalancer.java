/*
 * Copyright (c) 2003-2021 www.hualongxunda.com/ Inc. All rights reserved.
 * 注意：本内容仅限于深圳华龙讯达信息技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.tj.cloud.feign.rule.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.tj.cloud.feign.constant.GatewayConstant;
import com.tj.cloud.feign.rule.GrayLoadBalancer;
import com.tj.cloud.feign.vo.RouteDefinitionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/5
 * @Description:
 * @version:1.0
 */
public class VersionGrayLoadBalancer implements GrayLoadBalancer {

    private static final Logger log = LoggerFactory.getLogger(VersionGrayLoadBalancer.class);

    private final  DiscoveryClient discoveryClient;

    private final RedisTemplate<Object,Object> redisTemplate;


    public VersionGrayLoadBalancer(DiscoveryClient discoveryClient, RedisTemplate<Object,Object> redisTemplate) {
        this.discoveryClient = discoveryClient;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 根据serviceId 筛选可用服务
     *
     * @param serviceId 服务ID
     * @param request   当前请求
     * @return
     */
    @Override
    public ServiceInstance choose(String serviceId, ServerHttpRequest request) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        // 注册中心无实例 抛出异常
        if (CollUtil.isEmpty(instances)) {
            log.warn("No instance available for {}", serviceId);
            List<Object> cacheList = redisTemplate.opsForHash().values(GatewayConstant.ROUTE_KEY);
            List<RouteDefinitionVo> routeDefinitionVoList = new ArrayList<>();
            cacheList.forEach(s ->{
                routeDefinitionVoList.add((RouteDefinitionVo) s);
            });
            //
            String finalServiceId = serviceId;
            Optional<String> serviceNameOptional = routeDefinitionVoList.stream().filter(routeDefinitionVo -> StrUtil.equals(finalServiceId, routeDefinitionVo.getId()))
                    .map(RouteDefinitionVo::getRouteName).findFirst();
            if (serviceNameOptional.isPresent()) {
                serviceId = serviceNameOptional.get();
            }

            throw new NotFoundException(StrUtil.format("【{}】服务暂不可用，请稍后重试！", serviceId));
        }

        // 获取请求version，无则随机返回可用实例
        String reqVersion = request.getHeaders().getFirst(GatewayConstant.SERVICE_VERSION);
        if (StrUtil.isBlank(reqVersion)) {
            return instances.get(RandomUtil.randomInt(instances.size()));
        }

        // 遍历可以实例元数据，若匹配则返回此实例
        for (ServiceInstance instance : instances) {
            Map<String, String> metadata = instance.getMetadata();
            String targetVersion = MapUtil.getStr(metadata, GatewayConstant.SERVICE_VERSION);
            if (reqVersion.equalsIgnoreCase(targetVersion)) {
                log.debug("gray request match success :{} {}", reqVersion, instance);
                return instance;
            }
        }
        return instances.get(RandomUtil.randomInt(instances.size()));
    }
}
