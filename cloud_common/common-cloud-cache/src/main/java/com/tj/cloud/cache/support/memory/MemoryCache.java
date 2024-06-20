package com.tj.cloud.cache.support.memory;

import com.tj.cloud.core.abs.ICache;
import com.tj.cloud.core.enums.StatusCodeEnum;
import com.tj.cloud.core.exception.BusinessException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/26 13:41
 * * @version v1.0.0
 * * @desc 内存缓存 针对ICache 实现
 **/
public class MemoryCache<T> implements ICache<T> {

    private Map<String, T> map = new ConcurrentHashMap<>();

    @Override
    public void add(String key, T obj) {
        if (key == null) {
            return;
        }
        map.put(key, obj);
    }

    @Override
    public void delByKey(String key) {
        if (key == null) {
            return;
        }
        map.remove(key);
    }

    @Override
    public void clearAll() {
        map.clear();
    }

    @Override
    public T getByKey(String key) {
        if (key == null) {
            return null;
        }
        return map.get(key);
    }


    @Override
    public boolean containKey(String key) {
        if (key == null) {
            return false;
        }
        return map.containsKey(key);
    }

    @Override
    public List<T> findBySimpleKeys(String key) {
        CopyOnWriteArrayList<T> objects = new CopyOnWriteArrayList<>();
        map.keySet().parallelStream().forEach(s -> {
            if (s.contains(key)) {
                objects.add(map.get(s));
            }
        });
        return objects;
    }

    @Override
    public List<T> findBySimpleKeys(String region, String key) {
        return findBySimpleKeys(key);
    }

    @Override
    public void add(String key, T obj, long timeout) {
        throw new BusinessException(StatusCodeEnum.NOT_SUPPORT);
    }

    @Override
    public void addToRegion(String region, String key, T obj) {
        this.add(key, obj);
    }

    @Override
    public T getByKey(String region, String key) {
        return this.getByKey(key);
    }

    @Override
    public void clearRegion(String region) {

    }

    @Override
    public void delByKey(String region, String key) {
        this.delByKey(key);
    }

    @Override
    public boolean containKey(String region, String key) {
        return this.containKey(key);
    }

    @Override
    public void expireKey(String region, String key, int timeToLiveInSeconds) {
        expireKey(key, timeToLiveInSeconds);
    }

    @Override
    public void expireKey(String key, int timeToLiveInSeconds) {
        //这个map无法过期
    }

    @Override
    public Set<String> leftScan(String key) {
        return null;
    }

    @Override
    public Set<String> rightScan(String key) {
        return null;
    }
}
