package com.tj.cloud.cache.annotations;

import com.tj.cloud.cache.config.CloudCacheAutoConfiguration;
import com.tj.cloud.cache.properties.CloudCacheProperties;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * * @Author codingMan_tj * @Date 2024/3/25 13:33 * @version v1.0.0 * @desc 缓存类
 **/
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableConfigurationProperties(CloudCacheProperties.class)
@Import({ CloudCacheAutoConfiguration.class })
public @interface EnableCache {

}
