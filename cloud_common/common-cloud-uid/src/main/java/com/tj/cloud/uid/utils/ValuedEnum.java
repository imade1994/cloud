package com.tj.cloud.uid.utils;

/**
 * * @Author codingMan_tj
 * * @Date 2024/4/1 11:03
 * * @version v1.0.0
 * * @desc 定义枚举类并且 通过实现该方法  就可以使用{@link EnumUtils} 来格式化 枚举类 获取值
 **/
public interface ValuedEnum<T> {
    T value();
}
