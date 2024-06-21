package com.tj.cloud.core.context;

/**
 * * @Author codingMan_tj * @Date 2024/3/25 13:52 * @version v1.0.0 * @desc 获取当前用户信息
 **/
public interface ICurrentContext {

	/**
	 * 获取当前租户ID
	 * @return 租户ID
	 */
	String getCurrentTenantId();

	/**
	 * 获取当前租户名称
	 * @return 租户名称
	 */
	String getCurrentTenantName();

	/**
	 * 获取当前 用户ID
	 * @return 用户ID
	 */
	String getCurrentUserId();

	/**
	 * 获取当前用户名称
	 * @return 用户名称
	 *
	 */
	String getCurrentUserName();

	/**
	 * 或租当前用户组织ID
	 * @return 组织ID
	 *
	 */
	String getCurrentOrgId();

	/**
	 * 获取当前组织名称
	 * @return 组织名称
	 */
	String getCurrentOrgName();

}
