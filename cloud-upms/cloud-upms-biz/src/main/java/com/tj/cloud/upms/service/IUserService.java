/*
 * Copyright (c) 2003-2021 www.hualongxunda.com/ Inc. All rights reserved.
 * 注意：本内容仅限于深圳华龙讯达信息技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.tj.cloud.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tj.cloud.upms.entity.User;
import com.tj.cloud.upms.pojo.UserInfoPojo;

import java.util.List;
import java.util.Set;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
public interface IUserService extends IService<User> {


    UserInfoPojo getUserInfo(User user);


    /**
     * 根据部门 id 列表查询对应的用户 id 集合
     * @param deptIds 部门 id 列表
     * @return userIdList
     */
    List<String> listUserIdByDeptIds(Set<String> deptIds);
}
