package com.tj.cloud.cache.config;

import com.tj.cloud.cache.enums.CloudCacheType;
import com.tj.cloud.cache.properties.CloudCacheProperties;
import com.tj.cloud.cache.spring.CloudSpringCacheManager;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/26 14:07
 * * @version v1.0.0
 * * @desc
 **/
@Import(CloudCacheAutoConfiguration.CloudCacheConfigurationSelector.class)
@Configuration
public class CloudCacheAutoConfiguration {

    public static class CloudCacheConfigurationSelector implements ImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            CloudCacheType[] types = CloudCacheType.values();
            String[] imports = new String[types.length];
            for (int i = 0; i < types.length; i++) {
                imports[i] = types[i].getConfigurationClass().getName();
            }
            return imports;
        }
    }

    @Bean
    public CacheManager cacheManager(BeanFactory beanFactory, CloudCacheProperties abCacheProperties) {
        return new CloudSpringCacheManager(beanFactory, Boolean.TRUE.equals(abCacheProperties.getAllowNullValues()));
    }
}
