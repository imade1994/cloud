package com.tj.cloud.core.model;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/25 14:08
 * * @version v1.0.0
 * * @desc 用户ID抽象接口
 **/
public interface IdModel {

    /**
     * 主键
     * @return 返回用户ID
     */
    String getId();

    /**
     * 设置主键
     * @param id 用户ID
     */
    void setId(String id);




}
