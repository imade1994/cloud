package com.tj.cloud.mybatis.manager;

import java.io.Serializable;
import java.util.List;

/**
 * * @Author codingMan_tj
 * * @Date 2024/4/7 9:29
 * * @version v1.0.0
 * * @desc
 **/
public interface BaseManager<PK extends Serializable, T> {

    /**
     * 创建实体对象
     */
    void create(T entity);

    /**
     * 更新实体对象
     */
    void update(T entity);

    /**
     * 按实体ID删除对象
     */
    void remove(PK entityId);

    /**
     * 按实体ID获取实体
     */
    T get(PK entityId);

    /**
     * 按实体IDs删除记录
     */
    void removeByIds(PK... ids);

    /**
     * 按实体IDs删除记录
     */
    void removeByIdList(List<PK> idList);



}
