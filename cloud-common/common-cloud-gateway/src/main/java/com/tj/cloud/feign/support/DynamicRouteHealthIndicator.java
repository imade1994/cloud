/*
 * Copyright (c) 2003-2021 www.hualongxunda.com/ Inc. All rights reserved.
 * 注意：本内容仅限于深圳华龙讯达信息技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.tj.cloud.feign.support;

import com.tj.cloud.feign.constant.GatewayConstant;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/24
 * @Description:
 * @version:1.0
 */
@RequiredArgsConstructor
public class DynamicRouteHealthIndicator extends AbstractHealthIndicator {


    private static final Logger log = LoggerFactory.getLogger(DynamicRouteHealthIndicator.class);

    private final RedisTemplate<String,Object> redisTemplate;

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(GatewayConstant.ROUTE_KEY))) {
            builder.up();
        } else {
            log.warn("动态路由监控检查失败，当前无路由配置");
            builder.down();
        }
    }
}
