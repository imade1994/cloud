package com.tj.cloud.datasource.loadbalance;

import org.springframework.util.ReflectionUtils;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/28 11:11
 * * @version v1.0.0
 * * @desc 读写分离衡算法枚举
 **/
public enum DataSourceReplicaLoadBalanceAlgorithmType {

    /**
     * Round-robin replica load-balance algorithm.
     */
    ROUND_ROBIN("com.hlxd.cloud.db.core.readwrite.loadbalance.impl.RoundRobinReplicaLoadBalanceAlgorithm"),

    /**
     * Random replica load-balance algorithm
     */
    RANDOM("com.hlxd.cloud.db.core.readwrite.loadbalance.impl.RandomReplicaLoadBalanceAlgorithm");

    /**
     * clazz
     */
    private final String className;

    HlDataSourceReplicaLoadBalanceAlgorithmType(String className) {
        this.className = className;
    }

    public Class<?> loadClass() {
        try {
            return Class.forName(className);
        } catch (ReflectiveOperationException e) {
            ReflectionUtils.rethrowRuntimeException(e);
        }
        throw new IllegalStateException();
    }

    public String getClassName() {
        return className;
    }
}
