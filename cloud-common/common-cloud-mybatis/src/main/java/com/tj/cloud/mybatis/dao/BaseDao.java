package com.tj.cloud.mybatis.dao;

import java.io.Serializable;
import java.util.List;

/**
 * * @Author codingMan_tj * @Date 2024/4/7 9:32 * @version v1.0.0 * @desc
 **/
public interface BaseDao<PK extends Serializable, T> {

	/**
	 * 创建实体对象
	 */
	void create(T entity);

	/**
	 * 更新实体对象
	 */
	Integer update(T entity);

	/**
	 * 按实体ID删除对象
	 */
	void remove(PK entityId);

	/**
	 * 按实体ID获取实体
	 */
	T get(PK entityId);

	/**
	 * 取得所有查询对象
	 */
	List<T> query();

}
