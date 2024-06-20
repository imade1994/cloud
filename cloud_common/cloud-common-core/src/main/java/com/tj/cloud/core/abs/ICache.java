package com.tj.cloud.core.abs;

import java.util.List;
import java.util.Set;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/25 15:29
 * * @version v1.0.0
 * * @desc 缓存抽象类接口
 **/
public interface ICache<T extends Object> {

    /**
     * 添加缓存
     * @param key 缓存key
     * @param obj 缓存对象
     * @param timeToLiveInSeconds  存活时间
     */
    void add(String key, T obj, long timeToLiveInSeconds);

    /**
     * 添加缓存
     * @param key 缓存key
     * @param obj 缓存对象
     */
    void add(String key, T obj);

    /**
     * 根据键删除缓存
     * @param key 缓存key
     */
    void delByKey(String key);

    /**
     * 清除所有的缓存
     */
    void clearAll();

    /**
     * 读取缓存
     * @param key 缓存key
     * @return T 返回缓存对象
     */
    T getByKey(String key);


    /**
     * 包含key
     * @param key 缓存key
     * @return 返回是否存在缓存
     */
    boolean containKey(String key);

    /**
     * 查询满足条件的值
     * @param key 缓存key
     * @param region 缓存区
     * @return List<T> 返回缓存对象
     */
    List<T> findBySimpleKeys(String region, String key);

    /**
     * 查询满足条件的值
     * @param key 缓存key
     * @return List<T> 返回缓存对象
     */
    List<T> findBySimpleKeys(String key);


    /**
     * 在区域内添加缓存
     * @param key 缓存key
     * @param region 缓存区
     * @param obj 缓存对象
     */
    void addToRegion(String region, String key, T obj);

    /**
     * 获取区域内缓存
     * @param key 缓存key
     * @param region 缓存区
     * @return T 返回缓存对象
     */
    T getByKey(String region, String key);

    /**
     * 清除区域缓存
     * @param region 分区
     */
    void clearRegion(String region);

    /**
     * 根据键删除缓存
     * @param key 缓存key
     * @param region 缓存区
     */
    void delByKey(String region, String key);

    /**
     * 判断区域是否存在key
     * @param key 缓存key
     * @param region 缓存区
     */
    boolean containKey(String region, String key);

    /**
     * 设置缓存存活时间
     * @param key 缓存key
     * @param region 缓存区
     * @param timeToLiveInSeconds 存货时间
     */
    void expireKey(String region, String key, int timeToLiveInSeconds);


    /**
     * 过期缓存
     * @param key 缓存key
     * @param timeToLiveInSeconds 存货时间
     */
    void expireKey(String key, int timeToLiveInSeconds);


    /**
     * 左相似扫描
     * @param key 缓存key
     */
    Set<String> leftScan(String key);


    /**
     * 右相似扫描
     * @param key 缓存key
     */
    Set<String> rightScan(String key);
}
