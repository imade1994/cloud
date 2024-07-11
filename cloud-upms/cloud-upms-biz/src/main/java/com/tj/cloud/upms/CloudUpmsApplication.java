
package com.tj.cloud.upms;

import com.tj.cloud.cache.annotations.EnableCache;
import com.tj.cloud.feign.annotations.EnableCloudFeignClients;
import com.tj.cloud.security.annotations.EnableCloudResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@SpringBootApplication
@EnableCloudFeignClients
@EnableDiscoveryClient
@EnableCache
@MapperScan(basePackages = { "com.tj.cloud.*.mapper" })
@EnableCloudResourceServer
public class CloudUpmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudUpmsApplication.class, args);
	}

}
