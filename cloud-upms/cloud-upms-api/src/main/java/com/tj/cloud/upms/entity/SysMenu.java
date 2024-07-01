/*
 * Copyright (c) 2003-2021 www.hualongxunda.com/ Inc. All rights reserved.
 * 注意：本内容仅限于深圳华龙讯达信息技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.tj.cloud.upms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tj.cloud.mybatis.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@Data
@TableName("t_upms_sys_menu")
@EqualsAndHashCode(callSuper = true)
@ApiModel("岗位信息表")
public class SysMenu extends BaseModel {


    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @ApiModelProperty("菜单名称")
    private String name;

    /**
     * 菜单权限标识
     */
    @ApiModelProperty("菜单权限标识")
    private String permission;

    /**
     * 父菜单ID
     */
    @NotNull(message = "菜单父ID不能为空")
    @ApiModelProperty("菜单父id")
    private Long parentId;

    /**
     * 图标
     */
    @ApiModelProperty("菜单图标")
    private String icon;

    /**
     * 前端URL
     */
    @ApiModelProperty("前端路由标识路径")
    private String path;

    /**
     * 排序值
     */
    @ApiModelProperty("排序值")
    private Integer sortOrder;

    /**
     * 菜单类型 （0菜单 1按钮）
     */
    @ApiModelProperty("菜单类型不能为空")
    private String type;

    /**
     * 路由缓冲
     */
    @ApiModelProperty("路由缓冲")
    private String keepAlive;

    /**
     * 0--正常 1--删除
     */
    @TableLogic
    private String delFlag;
}
