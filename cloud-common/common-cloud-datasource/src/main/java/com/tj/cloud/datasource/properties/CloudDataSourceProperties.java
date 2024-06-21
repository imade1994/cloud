package com.tj.cloud.datasource.properties;

import com.tj.cloud.datasource.loadbalance.DataSourceReplicaLoadBalanceAlgorithmType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * * @Author codingMan_tj * @Date 2024/3/28 11:11 * @version v1.0.0 * @desc
 **/

@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class CloudDataSourceProperties {

	/**
	 * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
	 */
	private String driverClassName;

	/**
	 * JDBC URL of the database.
	 */
	private String url;

	/**
	 * Login username of the database.
	 */
	private String username;

	/**
	 * Login password of the database.
	 */
	private String password;

	/**
	 * 数据源类别
	 */
	private String dbType;

	/**
	 * 是否对无用链接回收
	 */
	private Boolean removeAbandoned = Boolean.TRUE;

	/**
	 * 在指定时间内没被使用的链接进行回收
	 */
	private Integer removeAbandonedTimeout = 120;

	/**
	 * 校验连接SQL
	 */
	private String validationQuery;

	/**
	 * 校验sql的超时时长
	 */
	private Integer validationQueryTimeout = 300000;

	/**
	 * 是否空闲时对连接校验
	 */
	private Boolean testWhileIdle = Boolean.TRUE;

	/**
	 * 对池内空闲链接的校验间隔时长/毫秒
	 */
	private Integer timeBetweenEvictionRunsMillis = 600000;

	/**
	 * 空闲了多久才能被释放/秒
	 */
	private Integer minEvictableIdleTimeMillis = 540000;

	/**
	 * 从池中借出链接时是否需要检验链接
	 */
	private Boolean testOnBorrow = Boolean.FALSE;

	/**
	 * 从池中返回链接是否需要检验链接
	 */
	private Boolean testOnReturn = Boolean.FALSE;

	/**
	 * Druid-初始化连接数
	 */
	private Integer initialSize = 5;

	/**
	 * Druid-最大连接数
	 */
	private Integer maxActive = 100;

	/**
	 * Druid-最小空闲连接数
	 */
	private Integer minIdle = 5;

	/**
	 * Druid-建⽴连接最⼤等待时间
	 */
	private Long maxWait = 60000L;

	/**
	 * replica load balance algorithm
	 *
	 * <ul>
	 * <ol>
	 * ROUND_ROBIN: Round-robin replica load-balance algorithm.
	 * </ol>
	 * <ol>
	 * RANDOM: Random replica load-balance algorithm.
	 * </ol>
	 * </ul>
	 */
	private DataSourceReplicaLoadBalanceAlgorithmType replicaLoadBalanceAlgorithm = DataSourceReplicaLoadBalanceAlgorithmType.ROUND_ROBIN;

	/**
	 * replica load balance algorithm implement
	 */
	private Class<?> replicaLoadBalanceAlgorithmClass;

	/**
	 * replicates properties
	 */
	private List<CloudDataSourceProperties> replicates;

	public Class<?> getReplicaLoadBalanceAlgorithmClass() {
		return replicaLoadBalanceAlgorithmClass != null ? replicaLoadBalanceAlgorithmClass
				: replicaLoadBalanceAlgorithm.loadClass();
	}

}
