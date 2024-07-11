
package com.tj.cloud.system;

import com.tj.cloud.cache.annotations.EnableCache;
import com.tj.cloud.feign.annotations.EnableCloudFeignClients;
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
public class CloudSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudSystemApplication.class, args);
	}

}
