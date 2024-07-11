
package com.tj.cloud.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tj.cloud.upms.entity.SysMenu;

import java.util.Set;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
public interface ISysMenuService extends IService<SysMenu> {

	/**
	 * 通过角色编号查询菜单
	 * @param roleId 角色ID
	 * @return
	 */
	Set<SysMenu> listMenusByRoleId(String roleId);

}
