package com.tj.cloud.core.abs;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/26 12:00
 * * @version v1.0.0
 * * @desc 配置获取抽象接口
 **/
public interface IProperty {

    /**
     * 获取配置
     * @param key 配置名
     * @return 配置的值
     * */
    String getValue(String key);
}
