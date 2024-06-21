package com.tj.cloud.datasource.readwrite.loadbalance.impl;

import com.tj.cloud.datasource.readwrite.loadbalance.ReplicaLoadBalanceAlgorithm;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * * @Author codingMan_tj * @Date 2024/3/28 11:17 * @version v1.0.0 * @desc
 **/
public class RandomReplicaLoadBalanceAlgorithm implements ReplicaLoadBalanceAlgorithm {

	@Override
	public void initialize(Environment environment) {

	}

	@Override
	public DataSource choice(List<DataSource> dataSources) {
		int index = ThreadLocalRandom.current().nextInt(dataSources.size());
		return dataSources.get(index);
	}

}
