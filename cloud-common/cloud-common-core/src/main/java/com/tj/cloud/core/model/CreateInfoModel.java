package com.tj.cloud.core.model;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * * @Author codingMan_tj * @Date 2024/3/25 14:06 * @version v1.0.0 * @desc 创建信息抽象接口
 **/
public interface CreateInfoModel {

	/**
	 * 创建时间
	 */
	LocalDateTime getCreateTime();

	/**
	 * 设置创建时间
	 */
	void setCreateTime(LocalDateTime createTime);

	/**
	 * 创建人ID
	 */
	String getCreateBy();

	/**
	 * 设置创建人ID
	 */
	void setCreateBy(String createBy);

	/**
	 * 更新时间
	 */
	LocalDateTime getUpdateTime();

	/**
	 * 设置 更新时间
	 */
	void setUpdateTime(LocalDateTime updateTime);

	/**
	 * 操作人
	 */
	String getUpdateBy();

	/**
	 * 设置更新人ID
	 */
	void setUpdateBy(String updateBy);

}
