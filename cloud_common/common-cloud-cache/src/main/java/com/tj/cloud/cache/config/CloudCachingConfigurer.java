package com.tj.cloud.cache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/26 14:11
 * * @version v1.0.0
 * * @desc
 **/
public class CloudCachingConfigurer extends CachingConfigurerSupport {

    private final CacheManager cacheManager;

    public CloudCachingConfigurer(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public CacheManager cacheManager() {
        return cacheManager;
    }
}
