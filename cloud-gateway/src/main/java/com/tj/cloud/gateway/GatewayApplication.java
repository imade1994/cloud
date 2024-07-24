package com.tj.cloud.gateway;

import com.tj.cloud.cache.annotations.EnableCache;
import com.tj.cloud.feign.annotations.EnableCloudDynamicRoute;
import com.tj.cloud.feign.annotations.EnableCloudFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/16
 * @Description:
 * @version:1.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableCloudFeignClients
@EnableDiscoveryClient
@EnableCache
@EnableCloudDynamicRoute
public class GatewayApplication {


    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }
}
