package com.tj.cloud.cache.spring;

import cn.hutool.core.util.StrUtil;
import com.tj.cloud.core.abs.ICache;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheDecorator;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * * @Author codingMan_tj * @Date 2024/3/26 14:09 * @version v1.0.0 * @desc
 **/
public class CloudSpringCacheManager implements CacheManager {

	private final ConcurrentHashMap<String, Cache> regionCacheMap;

	private final boolean allowNullValues;

	private final BeanFactory beanFactory;

	private ICache<Object> hlCache;

	public CloudSpringCacheManager(BeanFactory beanFactory, boolean allowNullValues) {
		this.beanFactory = beanFactory;
		this.regionCacheMap = new ConcurrentHashMap<>();
		this.allowNullValues = allowNullValues;
	}

	@SuppressWarnings("unchecked")
	private ICache<Object> getHlCache() {
		if (hlCache == null) {
			synchronized (this) {
				if (hlCache == null) {
					this.hlCache = beanFactory.getBean(ICache.class);
				}
			}
		}
		return this.hlCache;
	}

	@Override
	public Cache getCache(String name) {
		Cache cache = regionCacheMap.computeIfAbsent(StrUtil.nullToEmpty(name),
				region -> new CloudSpringCache(this.allowNullValues, getHlCache(), region));
		return new TransactionAwareCacheDecorator(cache);
	}

	@Override
	public Collection<String> getCacheNames() {
		return regionCacheMap.keySet();
	}

}
