package com.tj.cloud.datasource.readwrite.loadbalance;

import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.List;

/**
 * * @Author codingMan_tj * @Date 2024/3/28 11:17 * @version v1.0.0 * @desc
 **/
public interface ReplicaLoadBalanceAlgorithm {

	/**
	 * first initialize
	 * @param environment spring environment
	 */
	void initialize(Environment environment);

	/**
	 * choice datasource
	 * @param dataSources dataSources
	 * @return one of them
	 */
	DataSource choice(List<DataSource> dataSources);

}
