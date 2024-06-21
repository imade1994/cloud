package com.tj.cloud.datasource.dy;

import com.tj.cloud.core.utils.AppUtil;
import com.tj.cloud.datasource.util.DataSourceUtil;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * * @Author codingMan_tj * @Date 2024/3/28 11:32 * @version v1.0.0 * @desc
 **/
public class DbContextHolder {

	private static final ThreadLocal<String> contextHolderAlias = new ThreadLocal<String>();

	private static Map<String, String> dataSourceDbType = new ConcurrentHashMap<String, String>();

	protected static final Logger log = LoggerFactory.getLogger(DbContextHolder.class);

	/**
	 * 设置当前数据库
	 * @param dbAlias :数据源别名
	 * @param dbType ：数据源的类型：oracle,mysql... void
	 */
	public static void setDataSource(String dbAlias, String dbType) {
		contextHolderAlias.set(dbAlias);
		DbContextHolder.dataSourceDbType.put(dbAlias, dbType);

		try {
			// 切换线程的mybatis的数据库类型 主要是为了databaseId的sql写法
			SqlSessionFactoryBean sqlSessionFactoryBean = AppUtil.getBean(SqlSessionFactoryBean.class);
			if (sqlSessionFactoryBean != null && sqlSessionFactoryBean.getObject() != null
					&& sqlSessionFactoryBean.getObject().getConfiguration() != null) {
				sqlSessionFactoryBean.getObject().getConfiguration().setDatabaseId(dbType);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setDefaultDataSource() {
		contextHolderAlias.set(DataSourceUtil.DEFAULT_DATASOURCE);
	}

	/**
	 * 取得当前数据源。
	 */
	public static String getDataSource() {
		String str = (String) contextHolderAlias.get();
		return str == null ? DataSourceUtil.DEFAULT_DATASOURCE : str;
	}

	public static String getDbType() {
		String dataSourceAlias = contextHolderAlias.get();
		if (ObjectUtils.isEmpty(dataSourceAlias)) {
			dataSourceAlias = DataSourceUtil.DEFAULT_DATASOURCE;
		}

		String str = DbContextHolder.dataSourceDbType.get(dataSourceAlias);
		if (ObjectUtils.isEmpty(str)) {
			log.warn("cannot get current dataSourceDbType!");
		}
		return str;
	}

	/**
	 * 清除上下文数据
	 */
	public static void clearDataSource() {
		contextHolderAlias.remove();
	}

	/**
	 * 放置一个数据源的数据库类型到dataSourceDbType
	 */
	public static void putDataSourceDbType(String dsKey, String dbType) {
		dataSourceDbType.put(dsKey, dbType);
	}

	/**
	 * 从dataSourceDbType获取数据库类型
	 */
	public static String getDataSourceDbType(String dsKey) {
		return dataSourceDbType.get(dsKey);
	}

}
