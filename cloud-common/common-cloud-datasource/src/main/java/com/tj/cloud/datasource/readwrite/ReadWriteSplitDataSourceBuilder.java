package com.tj.cloud.datasource.readwrite;

import com.tj.cloud.datasource.readwrite.loadbalance.ReplicaLoadBalanceAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.util.List;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 10:03 * @version v1.0.0 * @desc
 **/
public class ReadWriteSplitDataSourceBuilder {

	private DataSource primary;

	private List<DataSource> replicates;

	private Class<? extends ReplicaLoadBalanceAlgorithm> loadBalanceAlgorithmClass;

	public ReadWriteSplitDataSourceBuilder withPrimary(DataSource primary) {
		this.primary = primary;
		return this;
	}

	public ReadWriteSplitDataSourceBuilder withReplicates(List<DataSource> replicates) {
		this.replicates = replicates;
		return this;
	}

	public ReadWriteSplitDataSourceBuilder withLoadBalanceAlgorithmClass(
			Class<? extends ReplicaLoadBalanceAlgorithm> loadBalanceAlgorithmClass) {
		this.loadBalanceAlgorithmClass = loadBalanceAlgorithmClass;
		return this;
	}

	public ReadWriteSplitDataSource build(Environment environment) {
		ReplicaLoadBalanceAlgorithm replicaLoadBalanceAlgorithm = null;
		try {
			replicaLoadBalanceAlgorithm = loadBalanceAlgorithmClass.getConstructor().newInstance();
		}
		catch (ReflectiveOperationException e) {
			ReflectionUtils.rethrowRuntimeException(e);
		}
		replicaLoadBalanceAlgorithm.initialize(environment);
		return new ReadWriteSplitDataSource(primary, replicates, replicaLoadBalanceAlgorithm);
	}

}
