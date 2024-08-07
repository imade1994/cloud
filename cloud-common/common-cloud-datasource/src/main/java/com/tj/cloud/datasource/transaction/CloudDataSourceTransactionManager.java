package com.tj.cloud.datasource.transaction;

import com.tj.cloud.datasource.dy.DbContextHolder;
import com.tj.cloud.datasource.dy.DynamicDataSource;
import com.tj.cloud.datasource.util.DataSourceUtil;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.ResourceTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 10:23 * @version v1.0.0 * @desc
 **/
@Getter
public class CloudDataSourceTransactionManager extends AbstractPlatformTransactionManager
		implements ResourceTransactionManager, InitializingBean {

	private static Logger log = LoggerFactory.getLogger(CloudDataSourceTransactionManager.class);

	/**
	 * 当前线程的顶层hlTxObject 调用HlCloudDataSourceTransactionManager.addDataSource的数据源全部归于这里处理
	 */
	private static ThreadLocal<CloudDataSourceTransactionObject> threadLocalTopAbTxObject = new ThreadLocal<>();

	/**
	 * 线程中是否有活跃的事务（进行过doBegin的事务）
	 */
	private static ThreadLocal<Boolean> transactionActive = new ThreadLocal<>();

	// private static boolean SEATA_PRESENT;

	static {
		ClassLoader classLoader = CloudDataSourceTransactionManager.class.getClassLoader();
		// SEATA_PRESENT =
		// ClassUtilsExtend.isPresent("io.seata.rm.datasource.DataSourceProxy",
		// classLoader);
	}

	/**
	 * <pre>
	 * 事务是否支持数据库层级别的只读
	 * 则会改变Statement的行为
	 * </pre>
	 */
	private boolean enforceReadOnly = false;

	private static DynamicDataSource dynamicDataSource;

	private static CloudDataSourceTransactionManager hlCloudDataSourceTransactionManager;

	public CloudDataSourceTransactionManager() {
		setNestedTransactionAllowed(true);
	}

	public void setEnforceReadOnly(boolean enforceReadOnly) {
		this.enforceReadOnly = enforceReadOnly;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.debug("Cloud 事务管理器已就绪");
	}

	@Override
	public Object getResourceFactory() {
		return dynamicDataSource;
	}

	/**
	 * 生成一个在整个事务处理都用到的资源
	 */
	@Override
	protected Object doGetTransaction() {
		CloudDataSourceTransactionObject hlTxObject = new CloudDataSourceTransactionObject();
		// 如果线程没有顶层，则说明这次是顶层事务
		if (threadLocalTopAbTxObject.get() == null) {
			threadLocalTopAbTxObject.set(hlTxObject);
			if (log.isDebugEnabled()) {
				log.debug("hl 事务编号[" + hlTxObject.getSerialNumber()
						+ "]为顶层事务开始，调用 hlCloudDataSourceTransactionManager.addDataSource 的数据源全部归于这里处理");
			}
		}
		else {
			if (log.isDebugEnabled()) {
				log.debug("进入 hl 事务编号[" + threadLocalTopAbTxObject.get().getSerialNumber() + "]");
			}
		}
		return hlTxObject;
	}

	/**
	 * 判断是否已存在事务
	 */
	@Override
	protected boolean isExistingTransaction(Object transaction) {
		if (transactionActive.get() == null) {
			return false;
		}
		return transactionActive.get();
	}

	/**
	 * 准备事务，获取链接
	 */
	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) throws TransactionException {
		CloudDataSourceTransactionObject txxObject = (CloudDataSourceTransactionObject) transaction;
		if (log.isDebugEnabled()) {
			log.debug("cloud 事务编号[" + txxObject.getSerialNumber() + "]开始");
		}
		txxObject.setDefinition(definition);
		// 先把本地数据源加入管理中
		addGlobalDataSource(DataSourceUtil.GLOBAL_DATASOURCE, dynamicDataSource, txxObject);
		// 标记线程已开启了事务
		transactionActive.set(true);
	}

	/**
	 * 增加一个数据源到线程中
	 */
	private static void addGlobalDataSource(String dsKey, DataSource dataSource,
			CloudDataSourceTransactionObject hlTxObject) {
		try {
			// 拿出dsKey的资源 txObject
			DataSourceTransactionObject txObject = hlTxObject.getDsTxObj(dsKey);
			if (txObject == null) {
				txObject = new DataSourceTransactionObject();
				hlTxObject.putDsTxObj(dsKey, txObject);
			}
			// 判断资源是否为空，或者资源是否在事务中要同步（则不允许其他事务重复使用）
			if (!txObject.hasConnectionHolder() || txObject.getConnectionHolder().isSynchronizedWithTransaction()) {
				// 尝试在资源中拿
				ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager
						.getResource(dataSource);
				if (conHolder != null) {
					// 标记为不是这次管理这次事务中生成的资源，后面回收资源时不回收
					txObject.setConnectionHolder(conHolder, false);
				}
				else {
					Connection newCon = dataSource.getConnection();
					if (log.isDebugEnabled()) {
						log.debug("在 hl 事务编号[" + hlTxObject.getSerialNumber() + "]中，" + "数据源别名[" + dsKey + "]打开连接成功");
					}
					// 标记为这次事务中生成的资源，需要回收资源
					txObject.setConnectionHolder(new ConnectionHolder(newCon), true);
				}
			}
			txObject.getConnectionHolder().setSynchronizedWithTransaction(true);
			Connection con = txObject.getConnectionHolder().getConnection();
			TransactionDefinition definition = hlTxObject.getDefinition();
			Integer previousIsolationLevel = DataSourceUtils.prepareConnectionForTransaction(con, definition);
			txObject.setPreviousIsolationLevel(previousIsolationLevel);
			// 设置这个链接必须要被恢复为自动提交，有些数据源的池是有这方面的需要的
			txObject.setMustRestoreAutoCommit(true);
			if (log.isDebugEnabled()) {
				log.debug("在 hl 事务编号[" + hlTxObject.getSerialNumber() + "]中，" + "设置数据源别名为[" + dsKey + "]的链接为手动提交");
			}
			con.setAutoCommit(false);

			prepareTransactionalConnection(con, definition);

			// 设置超时
			int timeout = staticDetermineTimeout(definition);
			if (timeout != TransactionDefinition.TIMEOUT_DEFAULT) {
				txObject.getConnectionHolder().setTimeoutInSeconds(timeout);
			}

			// 判断是否本次事务生成的新资源，需要绑定到资源中
			TransactionSynchronizationManager.bindResource(dataSource, txObject.getConnectionHolder());
		}
		catch (Throwable ex) {
			DataSourceTransactionObject txObject = hlTxObject.getDsTxObj(dsKey);
			// 释放和关闭这次事务的相关资源
			if (txObject != null && txObject.isNewConnectionHolder()) {
				DataSource ds = DataSourceUtil.getDataSourceByAlias(dsKey);
				Connection con = txObject.getConnectionHolder().getConnection();
				DataSourceUtils.releaseConnection(con, ds);
				txObject.setConnectionHolder(null, false);
			}
			throw new CannotCreateTransactionException(
					"在 hl 事务编号[" + hlTxObject.getSerialNumber() + "]中，" + "数据源别名[" + dsKey + "]打开连接错误", ex);
		}

	}

	/**
	 * 增加一个数据源到线程中，并作本地数据源的检查，防止重复打开链接
	 */
	public static void addDataSource(String dsKey, DataSource dataSource) {
		// 系统数据源去重
		if (dataSource == dynamicDataSource) {
			return;
		}
		// key跟本地数据源取的真正数据源是一样的且资源中无该ConnectionHolder，则拿出本地数据源的ConnectionHolder绑定到这个数据源中
		if (DbContextHolder.getDataSource().equals(dsKey)
				&& TransactionSynchronizationManager.getResource(dataSource) == null) {
			ConnectionHolder holder = threadLocalTopAbTxObject.get().getDsTxObj(DataSourceUtil.GLOBAL_DATASOURCE)
					.getConnectionHolder();
			TransactionSynchronizationManager.bindResource(dataSource, holder);
			return;
		}
		// 已加入过的也不需要加入
		if (threadLocalTopAbTxObject.get().getDsTxObjMap().containsKey(dsKey)) {
			return;
		}
		// 加入的是父事务
		addGlobalDataSource(dsKey, dataSource, threadLocalTopAbTxObject.get());
	}

	/**
	 * 挂起事务，把线程中的系统数据源ConnectionHolder先移除 注意！！这里传入的transaction是新事务的transaction！！！基本是一个空对象
	 * 所以我们只支持系统数据源挂起，hlDataSourceTransactionManager.addDataSource加入的数据源无法被挂起
	 * 则会在线程中一直重用着，其实不挂起的话，被其他事务所使用commit只要不释放就行，这样也能实现分段提交，感觉挂起意义不大。
	 */
	@Override
	protected Object doSuspend(Object transaction) {
		CloudDataSourceTransactionObject object = (CloudDataSourceTransactionObject) transaction;
		if (null == object.getDefinition() || null == object.getDsTxObjMap()) {
			return null;
		}
		else {
			return TransactionSynchronizationManager.unbindResource(dynamicDataSource);
		}
	}

	/**
	 * 恢复之前挂起的系统本地数据源ConnectionHolder
	 */
	@Override
	protected void doResume(Object transaction, Object suspendedResources) {
		TransactionSynchronizationManager.bindResource(dynamicDataSource, suspendedResources);
	}

	@Override
	protected void doCommit(DefaultTransactionStatus status) {
		CloudDataSourceTransactionObject hlTxObject = (CloudDataSourceTransactionObject) status.getTransaction();
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, DataSourceTransactionObject> entry : hlTxObject.getDsTxObjMap().entrySet()) {
			try {
				entry.getValue().getConnectionHolder().getConnection().commit();
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(entry.getKey());
				if (log.isDebugEnabled()) {
					log.debug("在 hl 事务编号[" + hlTxObject.getSerialNumber() + "]中，" + "数据源别名[" + entry.getKey()
							+ "]提交事务成功");
				}
			}
			catch (SQLException ex) {
				throw new TransactionSystemException(
						"数据源别名[" + entry.getKey() + "]提交事务失败，需要干预已提交成功的数据源别名[" + sb + "]的数据", ex);
			}
		}

	}

	/**
	 * 回滚
	 */
	@Override
	protected void doRollback(DefaultTransactionStatus status) {
		CloudDataSourceTransactionObject hlTxObject = (CloudDataSourceTransactionObject) status.getTransaction();
		for (Map.Entry<String, DataSourceTransactionObject> entry : hlTxObject.getDsTxObjMap().entrySet()) {
			try {
				entry.getValue().getConnectionHolder().getConnection().rollback();
				if (log.isDebugEnabled()) {
					log.debug("在 hl 事务编号[" + hlTxObject.getSerialNumber() + "]中，" + "数据源别名[" + entry.getKey()
							+ "]回滚事务成功");
				}
			}
			catch (SQLException ex) {
				throw new TransactionSystemException("数据源别名[" + entry.getKey() + "]回滚事务失败", ex);
			}
		}

	}

	/**
	 * 告诉所有数据源的ConnectionHolder，已被置为回滚状态
	 */
	@Override
	protected void doSetRollbackOnly(DefaultTransactionStatus status) {
		CloudDataSourceTransactionObject hlTxObject = (CloudDataSourceTransactionObject) status.getTransaction();
		for (Map.Entry<String, DataSourceTransactionObject> entry : hlTxObject.getDsTxObjMap().entrySet()) {
			if (status.isDebug()) {
				log.debug("在 hl 事务编号[" + hlTxObject.getSerialNumber() + "]中，" + "修改数据别名为 [" + entry.getKey()
						+ "]的链接资源为 rollback-only");
			}
			entry.getValue().setRollbackOnly();
		}
	}

	/**
	 * 回收链接ConnectionHolder
	 */
	@Override
	protected void doCleanupAfterCompletion(Object transaction) {
		CloudDataSourceTransactionObject hlTxObject = (CloudDataSourceTransactionObject) transaction;
		for (Map.Entry<String, DataSourceTransactionObject> entry : hlTxObject.getDsTxObjMap().entrySet()) {
			DataSourceTransactionObject txObject = entry.getValue();
			DataSource dataSource = DataSourceUtil.getDataSourceByAliasWithLocal(entry.getKey());
			// 如果ConnectionHolder是由本次事务产生，则回收
			if (txObject.isNewConnectionHolder()) {
				TransactionSynchronizationManager.unbindResource(dataSource);
			}
			// 重置链接属性
			Connection con = txObject.getConnectionHolder().getConnection();
			try {
				if (txObject.isMustRestoreAutoCommit()) {
					con.setAutoCommit(true);
				}
				DataSourceUtils.resetConnectionAfterTransaction(con, txObject.getPreviousIsolationLevel());
			}
			catch (Throwable ex) {
				log.debug("在 hl 事务编号[" + hlTxObject.getSerialNumber() + "]中，" + "在完成事务后，数据源别名为[" + entry.getKey()
						+ "]的属性无法被还原", ex);
			}
			if (txObject.isNewConnectionHolder()) {
				if (log.isDebugEnabled()) {
					log.debug("在hl 事务编号[" + hlTxObject.getSerialNumber() + "]中，" + "在完成事务后，释放数据源别名为[" + entry.getKey()
							+ "]的 jdbc 的链接");
				}
				DataSourceUtils.releaseConnection(con, dataSource);
			}

			txObject.getConnectionHolder().clear();
		}
		// 释放链接刚好是本地数据源的链接复用，而绑定了TransactionSynchronizationManager的资源
		DataSource dataSource = DataSourceUtil.getDataSourceByAlias(DbContextHolder.getDataSource());
		ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager.getResource(dataSource);
		if (conHolder != null) {
			TransactionSynchronizationManager.unbindResource(dataSource);
		}
		// 回收顶层事务的线程资源
		if (hlTxObject == threadLocalTopAbTxObject.get()) {
			threadLocalTopAbTxObject.remove();
			transactionActive.remove();// 清除事务标记
			if (log.isDebugEnabled()) {
				log.debug("hl 事务顶层事务编号[" + hlTxObject.getSerialNumber() + "]结束");
			}
		}
		else {
			if (log.isDebugEnabled()) {
				log.debug("hl 事务编号[" + hlTxObject.getSerialNumber() + "]结束");
			}
		}

	}

	/**
	 * 处理一下链接为只读链接
	 */
	private static void prepareTransactionalConnection(Connection con, TransactionDefinition definition)
			throws SQLException {
		boolean isEnforceReadOnly = hlCloudDataSourceTransactionManager.isEnforceReadOnly();
		if (isEnforceReadOnly && definition.isReadOnly()) {
			Statement stmt = con.createStatement();
			try {
				stmt.executeUpdate("SET TRANSACTION READ ONLY");
			}
			finally {
				stmt.close();
			}
		}
	}

	/**
	 * 静态方法取超时时
	 */
	private static int staticDetermineTimeout(TransactionDefinition definition) {
		if (definition.getTimeout() != TransactionDefinition.TIMEOUT_DEFAULT) {
			return definition.getTimeout();
		}
		return hlCloudDataSourceTransactionManager.getDefaultTimeout();
	}

	public void setDynamicDataSource(DynamicDataSource dynamicDataSource) {
		CloudDataSourceTransactionManager.dynamicDataSource = dynamicDataSource;
		CloudDataSourceTransactionManager.hlCloudDataSourceTransactionManager = this;
		DataSourceUtil.dynamicDataSource = dynamicDataSource;
	}

}
