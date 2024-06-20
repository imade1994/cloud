package com.tj.cloud.auth;

import com.tj.cloud.feign.annotations.EnableCloudFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * * @Author codingMan_tj
 * * @Date 2024/4/7 9:51
 * * @version v1.0.0
 * * @desc
 **/
@SpringBootApplication
@EnableCloudFeignClients
@EnableDiscoveryClient
public class CloudAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudAuthApplication.class,args);
    }
}
