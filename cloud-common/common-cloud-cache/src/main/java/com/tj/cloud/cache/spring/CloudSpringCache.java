package com.tj.cloud.cache.spring;

import cn.hutool.core.util.StrUtil;
import com.tj.cloud.core.abs.ICache;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractValueAdaptingCache;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * * @Author codingMan_tj * @Date 2024/3/26 14:09 * @version v1.0.0 * @desc
 **/
public class CloudSpringCache extends AbstractValueAdaptingCache {

	private final ICache<Object> cache;

	private final String region;

	public CloudSpringCache(boolean allowNullValues, ICache<Object> cache, String region) {
		super(allowNullValues);
		this.cache = cache;
		this.region = region;
	}

	private String serialString(Object key) {
		return (String) key;
	}

	@Override
	protected Object lookup(Object key) {
		Object cacheValue;
		if (StrUtil.isEmpty(region)) {
			cacheValue = cache.getByKey(serialString(key));
		}
		else {
			cacheValue = cache.getByKey(region, serialString(key));
		}
		return fromStoreValue(cacheValue);
	}

	@Override
	public String getName() {
		return region;
	}

	@Override
	public Object getNativeCache() {
		return cache;
	}

	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		Object storeValue = lookup(key);
		if (Objects.isNull(storeValue)) {
			synchronized (this) {
				storeValue = lookup(key);
				if (Objects.isNull(storeValue)) {
					try {
						storeValue = valueLoader.call();
					}
					catch (Exception ex) {
						throw new Cache.ValueRetrievalException(key, valueLoader, ex);
					}
					put(key, storeValue);
				}
			}
		}
		return Objects.isNull(storeValue) ? null : (T) storeValue;
	}

	@Override
	public void put(Object key, Object value) {
		if (StrUtil.isEmpty(region)) {
			cache.add(serialString(key), toStoreValue(value));
		}
		else {
			cache.addToRegion(region, serialString(key), toStoreValue(value));
		}
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		Object storeValue = toStoreValue(value);
		if (!Objects.isNull(lookup(key))) {
			put(key, storeValue);
		}
		return toValueWrapper(storeValue);
	}

	@Override
	public void evict(Object key) {
		if (StrUtil.isEmpty(region)) {
			cache.delByKey(serialString(key));
		}
		else {
			cache.delByKey(region, serialString(key));
		}
	}

	@Override
	public void clear() {
		if (StrUtil.isEmpty(region)) {
			cache.clearRegion(region);
		}
		else {
			cache.clearAll();
		}
	}

}
