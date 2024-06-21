/*
 * Copyright (c) 2003-2021 www.hualongxunda.com/ Inc. All rights reserved.
 * 注意：本内容仅限于深圳华龙讯达信息技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.tj.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.tj.cloud.core.model.base.BaseEntity;

import java.io.Serializable;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/18
 * @Description:
 * @version:1.0
 */
@Data
@TableName("t_dynamic_code_template")
public class DynamicTemplate extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "模板名称")
    private String templateName;

    @ApiModelProperty(value = "是否使用Controller模板")
    private boolean useController;

    @ApiModelProperty(value = "Controller内容")
    private String controllerContent;

    @ApiModelProperty(value = "是否使用Entity模板")
    private boolean useEntity;

    @ApiModelProperty(value = "Entity内容")
    private String entityContent;

    @ApiModelProperty(value = "是否使用Mapper模板")
    private boolean useMapper;

    @ApiModelProperty(value = "Mapper内容")
    private String mapperContent;

    @ApiModelProperty(value = "是否使用Service模板")
    private boolean useService;

    @ApiModelProperty(value = "Service内容")
    private String serviceContent;

    @ApiModelProperty(value = "是否使用ServiceImpl模板")
    private boolean useServiceImpl;

    @ApiModelProperty(value = "ServiceImpl内容")
    private String serviceImplContent;

    @ApiModelProperty(value = "是否使用自定义变量")
    private boolean useTemplatesVar;

    @ApiModelProperty(value = "自定义变量内容")
    private String templatesVarJson;

    @ApiModelProperty(value = "是否公开")
    private boolean publish;
}
