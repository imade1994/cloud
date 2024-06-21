package com.tj.cloud.cache.support.redis;

import com.tj.cloud.core.abs.ICache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.SerializationException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * * @Author codingMan_tj * @Date 2024/3/25 16:17 * @version v1.0.0 * @desc redis 针对icache
 * 实现
 **/
public class RedisCache<T extends Object> implements ICache<T> {

	private final Logger logger = LoggerFactory.getLogger(RedisCache.class);

	private RedisTemplate<String, T> redisTemplate;

	public RedisCache(RedisTemplate<String, T> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public synchronized void add(String key, T obj) {
		redisTemplate.boundValueOps(key).set(obj);
		logger.info("redis add " + key);
	}

	@Override
	public synchronized void add(String key, T obj, long timeout) {
		redisTemplate.boundValueOps(key).set(obj, timeout, TimeUnit.SECONDS);
		logger.info("redis add " + key + " timeout " + timeout);
	}

	@Override
	public synchronized void delByKey(String key) {
		redisTemplate.delete(key);
		logger.info("redis delByKey " + key);
	}

	@Override
	public void clearAll() {
		redisTemplate.execute((RedisCallback<Object>) redisConnection -> {
			redisConnection.flushDb();
			return null;
		});
		logger.info("redis flushDB");
	}

	@Override
	public synchronized T getByKey(String key) {
		try {
			return (T) redisTemplate.opsForValue().get(key);
		}
		catch (SerializationException e) {
			this.delByKey(key);
			logger.warn("获取缓存对象失败，反序列化失败，已经删除缓存：{}", key, e);
		}
		return null;
	}

	@Override
	public boolean containKey(String key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

	@Override
	public List<T> findBySimpleKeys(String region, String key) {
		return getValuesBySimpleKey(key, 100000L);
	}

	public List<T> getValuesBySimpleKey(String pattern, Long limit) {
		return (List<T>) redisTemplate.execute(new RedisCallback<List<T>>() {
			@Override
			public List<T> doInRedis(RedisConnection connection) throws DataAccessException {
				List<T> list = new ArrayList<>();
				try (Cursor<byte[]> cursor = connection
						.scan(ScanOptions.scanOptions().match(pattern).count(limit).build())) {
					while (cursor.hasNext()) {
						byte[] bytes = connection.get(cursor.next());
						T value = (T) redisTemplate.getValueSerializer().deserialize(bytes);
						list.add(value);
					}
				}
				return list;
			}
		});
	}

	@Override
	public List<T> findBySimpleKeys(String key) {
		return getValuesBySimpleKey(key, 100000L);
	}

	@Override
	public void addToRegion(String region, String key, T obj) {
		redisTemplate.opsForHash().put(region, key, obj);
	}

	@Override
	public T getByKey(String region, String key) {
		return (T) redisTemplate.opsForHash().get(region, key);
	}

	@Override
	public void clearRegion(String region) {
		redisTemplate.opsForHash().delete(region, (redisTemplate.opsForHash().keys(region)));
	}

	@Override
	public void delByKey(String region, String key) {
		redisTemplate.opsForHash().delete(region, key);
	}

	@Override
	public boolean containKey(String region, String key) {
		return redisTemplate.opsForHash().hasKey(region, key);
	}

	@Override
	public void expireKey(String region, String key, int timeToLiveInSeconds) {

	}

	@Override
	public void expireKey(String key, int timeToLiveInSeconds) {
		redisTemplate.boundValueOps(key).expire(timeToLiveInSeconds, TimeUnit.SECONDS);
	}

	@Override
	public Set<String> leftScan(String key) {
		return getSimpleKeys("*" + key, 10000L);
	}

	public Set<String> getSimpleKeys(String pattern, long limit) {

		Set<String> execute = (Set<String>) redisTemplate.execute(new RedisCallback<Set<String>>() {
			@Override
			public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
				Set<String> list = new HashSet<>();
				try (Cursor<byte[]> cursor = connection
						.scan(ScanOptions.scanOptions().match(pattern).count(limit).build())) {
					while (cursor.hasNext()) {
						String key = (String) redisTemplate.getKeySerializer().deserialize(cursor.next());
						list.add(key);
					}
				}
				return list;
			}
		});
		return execute;
	}

	@Override
	public Set<String> rightScan(String key) {
		return getSimpleKeys(key + "*", 10000L);
	}

}
