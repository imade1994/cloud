package com.tj.cloud.datasource.transaction;

import com.tj.cloud.datasource.util.DataSourceUtil;
import org.springframework.transaction.TransactionDefinition;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/29 10:25
 * * @version v1.0.0
 * * @desc
 **/
public class CloudDataSourceTransactionObject {

    /**
     * 事务流水号，用于跟踪问题
     */
    private String serialNumber;
    /**
     * 线程中的事务配置，就是txAdvice相关信息，如，read-only="true" isolation="READ_COMMITTED"等
     */
    private TransactionDefinition definition;
    /**
     * Map<数据源别名,DataSourceTransactionObject>
     */
    private Map<String, DataSourceTransactionObject> dsTxObjMap = new LinkedHashMap<>();

    public CloudDataSourceTransactionObject() {
        this.serialNumber = Integer.toHexString(this.hashCode());
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public TransactionDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(TransactionDefinition definition) {
        this.definition = definition;
    }

    public Map<String, DataSourceTransactionObject> getDsTxObjMap() {
        return dsTxObjMap;
    }

    /**
     * 需求的put
     * 需要保证本地数据源是最后一个链接
     * 因为commit时（或者说其他操作），需要最复杂，操作最多的本地数据源保持强一致性
     */
    public void putDsTxObj(String dsKey, DataSourceTransactionObject dsTxObj) {
        dsTxObjMap.put(dsKey, dsTxObj);
        DataSourceTransactionObject txObj = dsTxObjMap.remove(DataSourceUtil.GLOBAL_DATASOURCE);
        dsTxObjMap.put(DataSourceUtil.GLOBAL_DATASOURCE, txObj);
    }

    public DataSourceTransactionObject getDsTxObj(String dsKey) {
        return dsTxObjMap.get(dsKey);
    }
}
