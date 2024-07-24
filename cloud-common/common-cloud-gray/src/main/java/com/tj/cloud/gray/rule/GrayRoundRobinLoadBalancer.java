package com.tj.cloud.gray.rule;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.NacosServiceInstance;
import com.tj.cloud.feign.constant.GatewayConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/16
 * @Description:
 * @version:1.0
 */
public class GrayRoundRobinLoadBalancer extends RoundRobinLoadBalancer {

    private static final Logger log = LoggerFactory.getLogger(ReactiveLoadBalancer.class);


    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    private final String serviceId;

    /**
     * @param serviceInstanceListSupplierProvider a provider of
     *                                            {@link ServiceInstanceListSupplier} that will be used to get available instances
     * @param serviceId                           id of the service for which to choose an instance
     */
    public GrayRoundRobinLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
                                      String serviceId) {
        super(serviceInstanceListSupplierProvider, serviceId);
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.serviceId = serviceId;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                .getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next().map(serviceInstances -> getInstanceResponse(serviceInstances, request));

    }

    Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, Request request) {

        // 注册中心无可用实例 抛出异常
        if (CollUtil.isEmpty(instances)) {
            log.warn("No instance available serviceId: {}", serviceId);
            return new EmptyResponse();
        }

        if (request == null || request.getContext() == null) {
            return super.choose(request).block();
        }

        DefaultRequestContext requestContext = (DefaultRequestContext) request.getContext();
        if (!(requestContext.getClientRequest() instanceof RequestData)) {
            return super.choose(request).block();
        }

        RequestData clientRequest = (RequestData) requestContext.getClientRequest();
        HttpHeaders headers = clientRequest.getHeaders();

        String reqVersion = headers.getFirst(GatewayConstant.SERVICE_VERSION);
        if (StrUtil.isBlank(reqVersion)) {
            return super.choose(request).block();
        }

        // 遍历可以实例元数据，若匹配则返回此实例
        List<ServiceInstance> serviceInstanceList = instances.stream().filter(instance -> {
            NacosServiceInstance nacosInstance = (NacosServiceInstance) instance;
            Map<String, String> metadata = nacosInstance.getMetadata();
            String targetVersion = MapUtil.getStr(metadata, GatewayConstant.SERVICE_VERSION);
            return reqVersion.equalsIgnoreCase(targetVersion);
        }).collect(Collectors.toList());

        // 存在 随机返回
        if (CollUtil.isNotEmpty(serviceInstanceList)) {
            ServiceInstance instance = RandomUtil.randomEle(serviceInstanceList);

            log.debug("gray instance available serviceId: {} , instanceId: {}", serviceId, instance.getInstanceId());
            return new DefaultResponse(instance);
        } else {
            // 不存在,降级策略，使用轮询策略
            return super.choose(request).block();
        }
    }
}
