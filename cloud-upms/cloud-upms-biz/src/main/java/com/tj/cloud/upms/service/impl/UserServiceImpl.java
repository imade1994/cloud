/*
 * Copyright (c) 2003-2021 www.hualongxunda.com/ Inc. All rights reserved.
 * 注意：本内容仅限于深圳华龙讯达信息技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.tj.cloud.upms.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tj.cloud.upms.entity.SysMenu;
import com.tj.cloud.upms.entity.SysPost;
import com.tj.cloud.upms.entity.SysRole;
import com.tj.cloud.upms.entity.User;
import com.tj.cloud.upms.enumeration.MenuTypeEnum;
import com.tj.cloud.upms.mapper.SysMenuMapper;
import com.tj.cloud.upms.mapper.SysRoleMapper;
import com.tj.cloud.upms.mapper.UserMapper;
import com.tj.cloud.upms.pojo.UserInfoPojo;
import com.tj.cloud.upms.service.ISysMenuService;
import com.tj.cloud.upms.service.ISysPostService;
import com.tj.cloud.upms.service.ISysRoleService;
import com.tj.cloud.upms.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();


    @Resource
    ISysRoleService sysRoleService;


    @Resource
    ISysMenuService sysMenuService;


    @Resource
    ISysPostService sysPostService;






    @Override
    public UserInfoPojo getUserInfo(User user) {
        UserInfoPojo userInfoPojo = new UserInfoPojo();
        userInfoPojo.setUser(user);
        // 设置角色列表
        List<SysRole> roleList = sysRoleService.listRolesByUserId(user.getId());
        userInfoPojo.setRoleList(roleList);
        // 设置角色列表 （ID）
        List<String> roleIds = roleList.stream().map(SysRole::getId).collect(Collectors.toList());
        userInfoPojo.setRoles(ArrayUtil.toArray(roleIds, String.class));
        // 设置岗位列表
        List<SysPost> postList = sysPostService.listPostsByUserId(user.getId());
        userInfoPojo.setPostList(postList);
        // 设置权限列表（menu.permission）
        Set<String> permissions = roleIds.stream().map(sysMenuService::listMenusByRoleId).flatMap(Collection::stream)
                .filter(m -> MenuTypeEnum.BUTTON.getType().equals(m.getType())).map(SysMenu::getPermission)
                .filter(StrUtil::isNotBlank).collect(Collectors.toSet());
        userInfoPojo.setPermissions(ArrayUtil.toArray(permissions, String.class));
        return userInfoPojo;
    }

    @Override
    public List<String> listUserIdByDeptIds(Set<String> deptIds) {
        return this.listObjs(
                Wrappers.lambdaQuery(User.class).select(User::getId).in(User::getDeptId, deptIds),
                String.class::cast);
    }
}
