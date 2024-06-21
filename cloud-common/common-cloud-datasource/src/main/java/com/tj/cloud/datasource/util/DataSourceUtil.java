package com.tj.cloud.datasource.util;

import com.tj.cloud.datasource.dy.DbContextHolder;
import com.tj.cloud.datasource.dy.DynamicDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.util.Map;

/**
 * * @Author codingMan_tj * @Date 2024/3/28 11:29 * @version v1.0.0 * @desc
 **/
public class DataSourceUtil {

	public static final String GLOBAL_DATASOURCE = "dataSource";

	public static final String DEFAULT_DATASOURCE = "dataSourceDefault";

	public static final String TARGET_DATA_SOURCES = "targetDataSources";

	public static final String DRUID_DATASOURCE = "druidDataSource";// 阿里微服务seta事务的数据源

	public static DynamicDataSource dynamicDataSource;

	/**
	 * 添加数据源 。
	 * @param key
	 * @param dataSource void
	 * @param replace 若存在是否替代
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static void addDataSource(String key, DataSource dataSource, boolean replace) {
		if (dynamicDataSource.isDataSourceExist(key)) {
			if (!replace)
				return;
			dynamicDataSource.removeDataSource(key);
		}
		dynamicDataSource.addDataSource(key, dataSource);
	}

	/**
	 * <pre>
	 * 添加数据源 。
	 * </pre>
	 * @param key
	 * @param dataSource
	 * @param dbType 数据源类型，填写后会放置到DbContextHolder.dataSourceDbType中
	 * @param replace
	 */
	public static void addDataSource(String key, DataSource dataSource, String dbType, boolean replace) {
		addDataSource(key, dataSource, replace);
		if (!ObjectUtils.isEmpty(dbType)) {
			DbContextHolder.putDataSourceDbType(key, dbType);
		}
	}

	/**
	 * <pre>
	 * 判断在环境中是否已存在别名key的数据源
	 * </pre>
	 * @param key
	 * @return
	 */
	public static boolean isDataSourceExist(String key) {
		return dynamicDataSource.isDataSourceExist(key);
	}

	/**
	 * 根据名字删除数据源。
	 * @param key void
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static void removeDataSource(String key) throws IllegalAccessException, NoSuchFieldException {
		dynamicDataSource.removeDataSource(key);
	}

	/**
	 * 取得数据源
	 */
	public static Map<String, DataSource> getDataSources() {
		Map<String, DataSource> map = dynamicDataSource.getDataSource();
		return map;
	}

	/**
	 * 根据别名返回容器里对应的数据源
	 */
	public static DataSource getDataSourceByAlias(String alias) {
		Map<String, DataSource> map = getDataSources();
		return map.get(alias);
	}

	/**
	 * 根据别名返回容器里对应的数据源 如果是取的是DataSourceUtil.GLOBAL_DATASOURCE 则返回DynamicDataSource
	 * 系统的路由数据源
	 */
	public static DataSource getDataSourceByAliasWithLocal(String alias) {
		if (DataSourceUtil.GLOBAL_DATASOURCE.equals(alias)) {
			return dynamicDataSource;
		}
		return getDataSourceByAlias(alias);
	}

	/**
	 * 关闭数据源
	 */
	public static void closeDataSource(DataSource dataSource) throws Exception {
		if (dataSource == null) {
			return;
		}
		if (dataSource instanceof AutoCloseable) {
			((AutoCloseable) dataSource).close();
			return;
		}
		if (dataSource instanceof PooledDataSource) {
			((PooledDataSource) dataSource).forceCloseAll();
			return;
		}
		Class<?> targetClass = AopUtils.getTargetClass(dataSource);
		try {
			Class<?> dataSourceProxyClass = ClassUtils.forName("org.apache.tomcat.jdbc.pool.DataSourceProxy",
					Thread.currentThread().getContextClassLoader());
			if (dataSourceProxyClass.isAssignableFrom(dataSourceProxyClass)) {
				targetClass.getMethod("close", boolean.class).invoke(dataSource, true);
			}
		}
		catch (ReflectiveOperationException ignored) {
		}
	}

}
