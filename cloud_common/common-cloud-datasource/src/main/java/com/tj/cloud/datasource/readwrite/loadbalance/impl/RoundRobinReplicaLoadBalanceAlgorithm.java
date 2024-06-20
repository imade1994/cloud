package com.tj.cloud.datasource.readwrite.loadbalance.impl;

import com.tj.cloud.datasource.readwrite.loadbalance.ReplicaLoadBalanceAlgorithm;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/28 11:18
 * * @version v1.0.0
 * * @desc
 **/
public class RoundRobinReplicaLoadBalanceAlgorithm implements ReplicaLoadBalanceAlgorithm {

    private final AtomicInteger count = new AtomicInteger();

    @Override
    public void initialize(Environment environment) {
    }

    @Override
    public DataSource choice(List<DataSource> dataSources) {
        int index = dataSources.size() > 1 ? incrementAndGetModulo(dataSources.size()) : 0;
        return dataSources.get(index);
    }


    private int incrementAndGetModulo(int modulo) {
        for (; ; ) {
            int current = count.get();
            int next = (current + 1) % modulo;
            if (count.compareAndSet(current, next)) {
                return next;
            }
        }
    }
}
