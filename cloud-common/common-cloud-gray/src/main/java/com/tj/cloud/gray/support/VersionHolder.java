package com.tj.cloud.gray.support;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.experimental.UtilityClass;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/16
 * @Description:
 * @version:1.0
 */
@UtilityClass
public class VersionHolder {

    private final ThreadLocal<String> THREAD_LOCAL_VERSION = new TransmittableThreadLocal<>();

    /**
     * TTL 设置版本号<br/>
     *
     * @param version 版本号
     */
    public void setVersion(String version) {
        THREAD_LOCAL_VERSION.set(version);
    }

    /**
     * 获取TTL中的版本号
     *
     * @return 版本 || null
     */
    public String getVersion() {
        return THREAD_LOCAL_VERSION.get();
    }

    public void clear() {
        THREAD_LOCAL_VERSION.remove();
    }
}
