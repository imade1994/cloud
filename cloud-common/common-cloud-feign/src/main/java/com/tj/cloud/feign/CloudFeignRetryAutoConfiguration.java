
package com.tj.cloud.feign;

import com.tj.cloud.feign.retry.FeignRetryAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RetryTemplate.class)
public class CloudFeignRetryAutoConfiguration {

	@Bean
	public FeignRetryAspect feignRetryAspect() {
		return new FeignRetryAspect();
	}

}
