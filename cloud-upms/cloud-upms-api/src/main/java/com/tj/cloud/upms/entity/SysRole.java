/*
 * Copyright (c) 2003-2021 www.hualongxunda.com/ Inc. All rights reserved.
 * 注意：本内容仅限于深圳华龙讯达信息技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.tj.cloud.upms.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tj.cloud.mybatis.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_upms_sys_role")
public class SysRole extends BaseModel {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "角色名称 不能为空")
    @ApiModelProperty("角色名称")
    private String roleName;

    @NotBlank(message = "角色标识 不能为空")
    @ApiModelProperty("角色标识")
    private String roleCode;

    @NotBlank(message = "角色描述 不能为空")
    @ApiModelProperty("角色描述")
    private String roleDesc;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @TableLogic
    private String delFlag;
}
