package com.tj.cloud.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.tj.cloud.core.utils.AppUtil;
import com.tj.cloud.core.utils.FunctionUtil;
import com.tj.cloud.datasource.dy.DynamicDataSource;
import com.tj.cloud.datasource.event.DataSourceCloseEvent;
import com.tj.cloud.datasource.properties.CloudDataSourceProperties;
import com.tj.cloud.datasource.readwrite.ReadWriteSplitDataSourceBuilder;
import com.tj.cloud.datasource.readwrite.loadbalance.ReplicaLoadBalanceAlgorithm;
import com.tj.cloud.datasource.util.DataSourceUtil;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * * @Author codingMan_tj * @Date 2024/3/28 11:10 * @version v1.0.0 * @desc
 **/
@Configuration
@EnableConfigurationProperties(CloudDataSourceProperties.class)
public class CloudDataSourceAutoConfiguration {

	private final static Logger log = LoggerFactory.getLogger(CloudDataSourceAutoConfiguration.class);

	private static final boolean REPLICATE_PRESENT;

	// private static final boolean SEATA_PRESENT;

	public static final String POOL_NAME = "cloud-connection-Pool";

	static {
		ClassLoader classLoader = DataSourceAutoConfiguration.class.getClassLoader();
		REPLICATE_PRESENT = ClassUtils.isPresent("com.tj.cloud.datasource.readwrite.ReadWriteSplitDataSourceBuilder",
				classLoader);
	}

	private final CloudDataSourceProperties dataSourceProperties;

	private final Environment environment;

	public CloudDataSourceAutoConfiguration(CloudDataSourceProperties dataSourceProperties, Environment environment) {
		this.dataSourceProperties = dataSourceProperties;
		this.environment = environment;
	}

	private static DatabaseDriver databaseDriverFromJdbcUrl(String jdbcUrl) {
		jdbcUrl = jdbcUrl.replace(":p6spy:", ":");
		// 达梦数据库兼容oracle
		if (jdbcUrl.startsWith("jdbc:dm:")) {
			return DatabaseDriver.ORACLE;
		}
		return DatabaseDriver.fromJdbcUrl(jdbcUrl);
	}

	private static DataSource createDataSource(CloudDataSourceProperties dataSourceProperties) {
		log.warn("{} - Starting...", POOL_NAME);
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setName(POOL_NAME);
		String validateQuery = dataSourceProperties.getValidationQuery();
		if (ObjectUtils.isEmpty(validateQuery)) {
			DatabaseDriver databaseDriver = databaseDriverFromJdbcUrl(dataSourceProperties.getUrl());
			Assert.isTrue(!DatabaseDriver.UNKNOWN.equals(databaseDriver), () -> dataSourceProperties.getDbType()
					+ "  is not defined in org.springframework.boot.jdbc.DatabaseDriver");
			validateQuery = databaseDriver.getValidationQuery();
		}
		dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
		dataSource.setUrl(dataSourceProperties.getUrl());
		dataSource.setUsername(dataSourceProperties.getUsername());
		dataSource.setPassword(dataSourceProperties.getPassword());
		dataSource.setRemoveAbandoned(Boolean.TRUE.equals(dataSourceProperties.getRemoveAbandoned()));
		dataSource.setRemoveAbandonedTimeout(dataSourceProperties.getRemoveAbandonedTimeout());
		dataSource.setValidationQuery(validateQuery);
		// 初始化连接数，默认为5
		dataSource.setInitialSize(dataSourceProperties.getInitialSize());
		// 最大连接数，默认为100
		dataSource.setMaxActive(dataSourceProperties.getMaxActive());
		log.warn("{} - maxActive is: {}", POOL_NAME, dataSource.getMaxActive());
		// 最小空闲连接数，默认为5
		dataSource.setMinIdle(dataSourceProperties.getMinIdle());
		// 建⽴连接最⼤等待时间，默认为 60 秒
		dataSource.setMaxWait(dataSourceProperties.getMaxWait());
		// dataSource.setKeepAlive(true);
		dataSource.setValidationQueryTimeout(dataSourceProperties.getValidationQueryTimeout());
		dataSource.setTestWhileIdle(Boolean.TRUE.equals(dataSourceProperties.getTestWhileIdle()));
		// 对池内空闲链接的校验间隔时长/毫秒
		dataSource.setTimeBetweenEvictionRunsMillis(dataSourceProperties.getTimeBetweenEvictionRunsMillis());
		// 空闲了多久才能被释放/秒
		dataSource.setMinEvictableIdleTimeMillis(dataSourceProperties.getMinEvictableIdleTimeMillis());
		// 从池中借出链接时是否需要检验链接
		dataSource.setTestOnBorrow(Boolean.TRUE.equals(dataSourceProperties.getTestOnBorrow()));
		// 从池中返回链接是否需要检验链接
		dataSource.setTestOnReturn(Boolean.TRUE.equals(dataSourceProperties.getTestWhileIdle()));

		return dataSource;
	}

	private static DataSource createSeataDataSourceProxy(CloudDataSourceProperties dataSourceProperties) {
		return createDataSource(dataSourceProperties);
	}

	@SuppressWarnings("unchecked")
	private DataSource createReplicateDataSource() {
		DataSource primaryDataSource = createSeataDataSourceProxy(dataSourceProperties);
		List<DataSource> replicateDataSources = FunctionUtil
				.streamOfNullable(dataSourceProperties.getReplicates(), false)
				.map(CloudDataSourceAutoConfiguration::createDataSource).collect(Collectors.toList());
		return new ReadWriteSplitDataSourceBuilder().withPrimary(primaryDataSource).withReplicates(replicateDataSources)
				.withLoadBalanceAlgorithmClass((Class<? extends ReplicaLoadBalanceAlgorithm>) dataSourceProperties
						.getReplicaLoadBalanceAlgorithmClass())
				.build(environment);
	}

	@Bean
	public DynamicDataSource dataSource() throws Exception {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		ExpiringMap.Builder<Object, Object> builder = ExpiringMap.builder();
		// 允许属性有独立的可变的过期时间和策略
		builder.variableExpiration();
		// 设置过期时间 30分钟
		builder.expiration(30, TimeUnit.MINUTES);
		// 设置过期形式 ACCESSED：每次获取属性都会续期；CREATED：放进去时就开始计算，不会续期
		builder.expirationPolicy(ExpirationPolicy.ACCESSED);
		// 设置过期时的监听事件
		builder.expirationListener((key, val) -> {
			log.info("数据源【{}】超时没被使用被回收", key);
			AppUtil.publishEvent(new DataSourceCloseEvent(val));
			if (log.isInfoEnabled()) {
				log.info("现在数据源池中还有：{}", DataSourceUtil.getDataSources().keySet());
			}
		});
		ExpiringMap<Object, Object> targetDataSources = builder.build();
		// PS：遇到一个坑，当我把日数 设置 Integer.MAX_VALUE时，不知道为啥整个计算逻辑都失败了……所以这里设置1年就可以被当作不会过期了
		// 默认数据源不会被过期
		targetDataSources.put(DataSourceUtil.DEFAULT_DATASOURCE,
				REPLICATE_PRESENT ? createReplicateDataSource() : createSeataDataSourceProxy(dataSourceProperties),
				ExpirationPolicy.ACCESSED, 365, TimeUnit.DAYS);
		dynamicDataSource.setTargetDataSources(targetDataSources);
		dynamicDataSource.setDefaultDbType(dataSourceProperties.getDbType());
		log.warn("{} - Start completed.", POOL_NAME);
		return dynamicDataSource;
	}

}
