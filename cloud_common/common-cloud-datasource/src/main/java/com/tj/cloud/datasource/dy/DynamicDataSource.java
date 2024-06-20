package com.tj.cloud.datasource.dy;

import com.tj.cloud.core.exception.BusinessException;
import com.tj.cloud.core.utils.AppUtil;
import com.tj.cloud.datasource.event.DataSourceCloseEvent;
import com.tj.cloud.datasource.exception.DataSourceException;
import com.tj.cloud.datasource.util.DataSourceUtil;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/28 11:31
 * * @version v1.0.0
 * * @desc
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.getDataSource();
    }

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        // 重点
        super.afterPropertiesSet();
    }

    public void setDefaultDbType(String dbType) {
        DbContextHolder.setDataSource("dataSourceDefault", dbType);

    }


    private static Object getValue(Object instance, String fieldName) {
        try {
            Field field = AbstractRoutingDataSource.class.getDeclaredField(fieldName);
            // 参数值为true，禁用访问控制检查
            field.setAccessible(true);
            return field.get(instance);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 添加数据源。
     */
    public void addDataSource(String key, Object dataSource) {
        try {
            Map<Object, Object> targetDataSources = (Map<Object, Object>) getValue(this, DataSourceUtil.TARGET_DATA_SOURCES);
            boolean rtn = isDataSourceExist(key);
            if (rtn) {
                throw new DataSourceException("datasource name :" + key + "is exists!");
            }
            targetDataSources.put(key, dataSource);
            setTargetDataSources(targetDataSources);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 指定数据源是否存在。
     */
    public boolean isDataSourceExist(String key) {
        try {
            Map<Object, Object> targetDataSources = (Map<Object, Object>) getValue(this, DataSourceUtil.TARGET_DATA_SOURCES);
            return targetDataSources.containsKey(key);
        } catch (Exception e) {
            throw new BusinessException(e);
        }

    }

    /**
     * 根据别名删除数据源。
     */
    public void removeDataSource(String key) {
        Map<Object, Object> targetDataSources = (Map<Object, Object>) getValue(this, DataSourceUtil.TARGET_DATA_SOURCES);
        if (!targetDataSources.containsKey(key) || key.equals(DataSourceUtil.GLOBAL_DATASOURCE) || key.equals(DataSourceUtil.DEFAULT_DATASOURCE)) {
            return;
        }
        AppUtil.publishEvent(new DataSourceCloseEvent(targetDataSources.get(key)));
        targetDataSources.remove(key);
        setTargetDataSources(targetDataSources);
    }

    /**
     * 返回当前有哪些数据源。
     *
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchFieldException   Map<String,DataSource>
     */
    public Map<String, DataSource> getDataSource() {
        Map<String, DataSource> targetDataSources = (Map<String, DataSource>) getValue(this, DataSourceUtil.TARGET_DATA_SOURCES);
        return targetDataSources;

    }

    @Override
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
    }

    @Override
    public void setDataSourceLookup(DataSourceLookup dataSourceLookup) {
        super.setDataSourceLookup(dataSourceLookup);
    }

}
