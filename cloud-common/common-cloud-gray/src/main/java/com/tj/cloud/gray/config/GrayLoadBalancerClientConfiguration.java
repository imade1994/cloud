
package com.tj.cloud.gray.config;


import com.tj.cloud.gray.filter.GrayReactiveLoadBalancerClientFilter;
import com.tj.cloud.gray.rule.GrayLoadBalancer;
import com.tj.cloud.gray.rule.GrayRoundRobinLoadBalancer;
import com.tj.cloud.gray.rule.impl.VersionGrayLoadBalancer;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.config.GatewayReactiveLoadBalancerClientAutoConfiguration;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/5
 * @Description:
 * @version:1.0
 */
@Configuration
@EnableConfigurationProperties(LoadBalancerProperties.class)
@ConditionalOnProperty(value = "gray.rule.enabled", havingValue = "true")
@AutoConfigureBefore(GatewayReactiveLoadBalancerClientAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class GrayLoadBalancerClientConfiguration {

    @Bean
    public ReactiveLoadBalancerClientFilter gatewayLoadBalancerClientFilter(GrayLoadBalancer grayLoadBalancer,
                                                                            LoadBalancerProperties properties, GatewayLoadBalancerProperties loadBalancerProperties) {
        return new GrayReactiveLoadBalancerClientFilter(loadBalancerProperties, properties, grayLoadBalancer);
    }

    @Bean
    public GrayLoadBalancer grayLoadBalancer(DiscoveryClient discoveryClient, RedisTemplate<Object,Object> redisTemplate) {
        return new VersionGrayLoadBalancer(discoveryClient, redisTemplate);
    }


    @Bean
    @ConditionalOnMissingBean
    public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(Environment environment,
                                                                                   LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new GrayRoundRobinLoadBalancer(
                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }


    @Bean
    public RequestInterceptor grayFeignRequestInterceptor() {
        return new GrayFeignRequestInterceptor();
    }
}
