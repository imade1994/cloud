package com.tj.cloud.mybatis.manager.impl;

import com.alibaba.fastjson.annotation.JSONField;
import com.tj.cloud.mybatis.dao.BaseDao;
import com.tj.cloud.mybatis.manager.BaseManager;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * * @Author codingMan_tj * @Date 2024/4/7 9:33 * @version v1.0.0 * @desc
 **/
public class BaseManagerImpl<PK extends Serializable, T extends Serializable> implements BaseManager<PK, T> {

	@Resource
	protected BaseDao<PK, T> baseDao;

	public void create(T entity) {
		baseDao.create(entity);
	}

	public void update(T entity) {
		baseDao.update(entity);
	}

	public void remove(PK entityId) {
		baseDao.remove(entityId);
	}

	public T get(PK entityId) {
		return baseDao.get(entityId);
	}

	public void removeByIds(PK... ids) {
		if (ids != null) {
			for (PK pk : ids) {
				remove(pk);
			}
		}
	}

	public void removeByIdList(List<PK> idList) {
		if (idList != null) {
			for (PK pk : idList) {
				remove(pk);
			}
		}
	}

	@JSONField(serialize = false)
	public List<T> getAll() {
		return baseDao.query();
	}

}
