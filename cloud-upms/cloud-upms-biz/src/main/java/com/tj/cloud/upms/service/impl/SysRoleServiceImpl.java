
package com.tj.cloud.upms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tj.cloud.upms.entity.SysRole;
import com.tj.cloud.upms.mapper.SysRoleMapper;
import com.tj.cloud.upms.service.ISysRoleService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

	@Override
	public List<SysRole> listRolesByUserId(String userId) {
		return this.baseMapper.listRolesByUserId(userId);
	}

}
