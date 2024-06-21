package com.tj.cloud.datasource.readwrite;

import com.tj.cloud.datasource.readwrite.loadbalance.ReplicaLoadBalanceAlgorithm;
import com.tj.cloud.datasource.util.DataSourceUtil;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * * @Author codingMan_tj * @Date 2024/3/28 11:16 * @version v1.0.0 * @desc
 **/
public class ReadWriteSplitDataSource extends AbstractDataSource implements AutoCloseable {

	private final DataSource primary;

	private final List<DataSource> replicates;

	private final ReplicaLoadBalanceAlgorithm loadBalanceAlgorithm;

	public ReadWriteSplitDataSource(DataSource primary, List<DataSource> replicates,
			ReplicaLoadBalanceAlgorithm loadBalanceAlgorithm) {
		this.primary = primary;
		this.replicates = replicates;
		this.loadBalanceAlgorithm = loadBalanceAlgorithm;
	}

	private DataSource getDataSource() {
		return ObjectUtils.isEmpty(replicates) || !ReadWriteSplitHolder.isReadOnly() ? primary
				: loadBalanceAlgorithm.choice(replicates);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return getDataSource().getConnection(username, password);
	}

	@Override
	public void close() throws Exception {
		DataSourceUtil.closeDataSource(primary);
		for (DataSource dataSource : replicates) {
			DataSourceUtil.closeDataSource(dataSource);
		}
	}

}
