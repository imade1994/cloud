package com.tj.cloud.cache.support.j2cache.core;

import com.tj.cloud.cache.support.j2cache.constant.J2CacheConstant;
import com.tj.cloud.core.abs.ICache;
import net.oschina.j2cache.CacheChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/25 15:46
 * * @version v1.0.0
 * * @desc J2Cache 对于 抽象ICache 的实现
 **/
public class J2CacheImpl<T extends Object> implements ICache<T> {


    private final Logger logger = LoggerFactory.getLogger(J2CacheImpl.class);

    @Resource
    private CacheChannel cacheChannel;

    @Deprecated
    @Override
    public void add(String key, Object obj, long timeToLiveInSeconds) {
        cacheChannel.set(J2CacheConstant.DEFAULT_REGION, key, obj, timeToLiveInSeconds);
    }


    @Override
    public void add(String key, Object obj) {
        this.addToRegion(J2CacheConstant.DEFAULT_REGION, key, obj);
    }

    @Override
    public void delByKey(String key) {
        this.delByKey(J2CacheConstant.DEFAULT_REGION, key);
        cacheChannel.evict(J2CacheConstant.DEFAULT_REGION, key);
    }

    @Override
    public void clearAll() {
        cacheChannel.regions().forEach(region -> {
            cacheChannel.clear(region.getName());
            logger.info("J2Cache Region:{} clear !", region.getName());
        });
    }

    @Override
    public T getByKey(String key) {
        return this.getByKey(J2CacheConstant.DEFAULT_REGION, key);
    }


    @Override
    public boolean containKey(String key) {
        return this.containKey(J2CacheConstant.DEFAULT_REGION, key);
    }

    @Override
    public List<T> findBySimpleKeys(String key) {
        CopyOnWriteArrayList<T> returnList = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> keys = (CopyOnWriteArrayList<String>) cacheChannel.keys(J2CacheConstant.DEFAULT_REGION);
        keys.parallelStream().forEach(s -> {
            if (s.contains(key)) {
                returnList.add(this.getByKey(J2CacheConstant.DEFAULT_REGION, s));
            }
        });
        return returnList;
    }

    @Override
    public List<T> findBySimpleKeys(String region, String key) {
        CopyOnWriteArrayList<T> returnList = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> keys = (CopyOnWriteArrayList<String>) cacheChannel.keys(region);
        keys.parallelStream().forEach(s -> {
            if (s.contains(key)) {
                returnList.add(this.getByKey(region, s));
            }
        });
        return returnList;
    }


    @Override
    public void addToRegion(String region, String key, Object obj) {
        Assert.notNull(region,"分区不可以为空");
        Assert.notNull(key,"key 不可以为空");

        cacheChannel.set(region, key, obj);
    }


    @Override
    public T getByKey(String region, String key) {
        Assert.notNull(region,"分区不可以为空");
        Assert.notNull(key,"key 不可以为空");
        return (T) cacheChannel.get(region, key).getValue();
    }


    @Override
    public void clearRegion(String region) {
        Assert.notNull(region,"分区不可以为空");

        cacheChannel.clear(region);
    }


    @Override
    public void delByKey(String region, String key) {
        Assert.notNull(region,"分区不可以为空");
        Assert.notNull(key,"key 不可以为空");

        cacheChannel.evict(region, key);
    }


    @Override
    public boolean containKey(String region, String key) {
        Assert.notNull(region,"分区不可以为空");
        Assert.notNull(key,"key 不可以为空");

        return cacheChannel.exists(region, key);
    }

    @Override
    public void expireKey(String region, String key, int timeToLiveInSeconds) {


    }

    @Override
    public void expireKey(String key, int timeToLiveInSeconds) {

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
