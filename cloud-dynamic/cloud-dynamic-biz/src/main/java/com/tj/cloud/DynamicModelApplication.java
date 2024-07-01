
package com.tj.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/14
 * @Description:
 * @version:1.0
 */
@SpringBootApplication
@MapperScan(basePackages = { "com.tj.cloud.mapper" })
public class DynamicModelApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicModelApplication.class, args);
	}

}
