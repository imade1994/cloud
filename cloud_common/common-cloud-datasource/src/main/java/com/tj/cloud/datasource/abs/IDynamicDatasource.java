package com.tj.cloud.datasource.abs;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/28 11:09
 * * @version v1.0.0
 * * @desc 抽象获取数据源接口
 **/
public interface IDynamicDatasource {
    /**
     * 根据数据源别名获取数据库类型
     * @param alias 数据库别名
     */
    String getDbTypeByAlias(String alias);
}
