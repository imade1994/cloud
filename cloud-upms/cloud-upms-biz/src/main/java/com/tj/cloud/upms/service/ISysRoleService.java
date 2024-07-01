/*
 * Copyright (c) 2003-2021 www.hualongxunda.com/ Inc. All rights reserved.
 * 注意：本内容仅限于深圳华龙讯达信息技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
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
