package com.tj.cloud.feign;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tj.cloud.feign.sentinel.ext.CloudSentinelFeign;
import com.tj.cloud.feign.sentinel.handle.CloudUrlBlockHandler;
import com.tj.cloud.feign.sentinel.parser.CloudHeaderRequestOriginParser;
import feign.Feign;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.TimeUnit;

/**
 * * @Author codingMan_tj * @Date 2024/3/26 16:00 * @version v1.0.0 * @desc
 **/
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class CloudFeignAutoConfiguration {

	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "feign.sentinel.enabled")
	public Feign.Builder feignSentinelBuilder() {
		return CloudSentinelFeign.builder();
	}

	@Bean
	@ConditionalOnMissingBean
	public BlockExceptionHandler blockExceptionHandler(ObjectMapper objectMapper) {
		return new CloudUrlBlockHandler(objectMapper);
	}

	@Bean
	@ConditionalOnMissingBean
	public RequestOriginParser requestOriginParser() {
		return new CloudHeaderRequestOriginParser();
	}

	/**
	 * OkHttp 客户端配置
	 * @return OkHttp 客户端配
	 */
	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient.Builder().retryOnConnectionFailure(false) // 是否开启缓存
				.connectTimeout(30L, TimeUnit.SECONDS) // 连接超时时间
				.readTimeout(30L, TimeUnit.SECONDS) // 读取超时时间
				.writeTimeout(30L, TimeUnit.SECONDS).followRedirects(true) // 是否允许重定向
				.build();
	}

}
