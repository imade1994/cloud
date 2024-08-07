
package com.tj.cloud.upms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tj.cloud.upms.entity.SysMenu;
import com.tj.cloud.upms.mapper.SysMenuMapper;
import com.tj.cloud.upms.service.ISysMenuService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

	@Override
	public Set<SysMenu> listMenusByRoleId(String roleId) {
		return this.baseMapper.listMenusByRoleId(roleId);
	}

}
