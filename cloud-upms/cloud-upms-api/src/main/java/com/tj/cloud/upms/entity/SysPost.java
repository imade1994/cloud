/*
 * Copyright (c) 2003-2021 www.hualongxunda.com/ Inc. All rights reserved.
 * 注意：本内容仅限于深圳华龙讯达信息技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.tj.cloud.upms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tj.cloud.mybatis.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@Data
@TableName("t_upms_sys_post")
@EqualsAndHashCode(callSuper = true)
@ApiModel("岗位信息表")
public class SysPost extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 岗位编码
     */
    @ApiModelProperty("岗位编码")
    private String postCode;

    /**
     * 岗位名称
     */
    @ApiModelProperty("岗位名称")
    private String postName;

    /**
     * 岗位排序
     */
    @ApiModelProperty("岗位排序")
    private Integer postSort;

    /**
     * 是否删除 -1：已删除 0：正常
     */
    @ApiModelProperty("是否删除  -1：已删除  0：正常")
    private String delFlag;

    /**
     * 备注信息
     */
    @ApiModelProperty("备注信息")
    private String remark;
}

