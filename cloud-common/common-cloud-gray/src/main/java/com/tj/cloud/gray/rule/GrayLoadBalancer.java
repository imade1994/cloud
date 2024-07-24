
package com.tj.cloud.gray.rule;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/5
 * @Description:
 * @version:1.0
 */
public interface GrayLoadBalancer {

    /**
     * 根据serviceId 筛选可用服务
     *
     * @param serviceId 服务ID
     * @param request   当前请求
     * @return ServiceInstance 服务实例
     */
    ServiceInstance choose(String serviceId, ServerHttpRequest request);
}
