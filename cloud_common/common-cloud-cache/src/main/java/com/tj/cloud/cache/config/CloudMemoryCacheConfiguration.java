package com.tj.cloud.cache.config;

import com.tj.cloud.cache.support.memory.MemoryCache;
import com.tj.cloud.core.abs.ICache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/26 13:42
 * * @version v1.0.0
 * * @desc 基于内存的缓存实现
 **/
@Conditional(CloudCacheConditional.class)
public class CloudMemoryCacheConfiguration {


    @Bean
    public ICache iCache() {
        return new MemoryCache();
    }
}
