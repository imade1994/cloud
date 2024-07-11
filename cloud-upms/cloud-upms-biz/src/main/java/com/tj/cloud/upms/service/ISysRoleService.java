
package com.tj.cloud.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tj.cloud.upms.entity.SysRole;

import java.util.List;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
public interface ISysRoleService extends IService<SysRole> {

	/**
	 * 通过用户ID，查询角色信息
	 * @param userId
	 * @return
	 */
	List<SysRole> listRolesByUserId(String userId);

}
