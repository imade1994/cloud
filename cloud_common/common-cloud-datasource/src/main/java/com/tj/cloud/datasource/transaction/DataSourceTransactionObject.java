package com.tj.cloud.datasource.transaction;

import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.JdbcTransactionObjectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionSynchronizationUtils;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/29 10:25
 * * @version v1.0.0
 * * @desc
 **/
public class DataSourceTransactionObject extends JdbcTransactionObjectSupport {

    private boolean newConnectionHolder;

    private boolean mustRestoreAutoCommit;

    public void setConnectionHolder(ConnectionHolder connectionHolder, boolean newConnectionHolder) {
        super.setConnectionHolder(connectionHolder);
        this.newConnectionHolder = newConnectionHolder;
    }

    public boolean isNewConnectionHolder() {
        return this.newConnectionHolder;
    }

    public void setMustRestoreAutoCommit(boolean mustRestoreAutoCommit) {
        this.mustRestoreAutoCommit = mustRestoreAutoCommit;
    }

    public boolean isMustRestoreAutoCommit() {
        return this.mustRestoreAutoCommit;
    }

    public void setRollbackOnly() {
        getConnectionHolder().setRollbackOnly();
    }

    @Override
    public boolean isRollbackOnly() {
        return getConnectionHolder().isRollbackOnly();
    }

    @Override
    public void flush() {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationUtils.triggerFlush();
        }
    }

}
