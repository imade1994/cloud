package com.tj.cloud.core.model;

import java.util.List;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/25 14:11
 * * @version v1.0.0
 * * @desc 抽象树形接口
 **/
public interface Tree<C extends Tree<?>> {

    /**
     * 获取主键ID
     * @return 获取主键ID
     */
    String getId();

    /**
     * 获取父ID
     * @return 获取树形结构父级ID
     */
    String getParentId();

    /**
     * 获取子对象
     * @return 货期当前子对象
     */
    List<C> getChildren();

    /**
     * 设置子对象
     * @param list 设置当前 子对象
     */
    void setChildren(List<C> list);

}
