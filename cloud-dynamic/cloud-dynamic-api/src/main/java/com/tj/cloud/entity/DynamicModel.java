/*
 * Copyright (c) 2003-2021 www.hualongxunda.com/ Inc. All rights reserved.
 * 注意：本内容仅限于深圳华龙讯达信息技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.tj.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tj.cloud.core.model.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/14
 * @Description:
 * @version:1.0
 */
@Getter
@Setter
@TableName("t_dynamic_code")
@Builder
public class DynamicModel extends BaseEntity implements Serializable {


    @ApiModelProperty(value = "模板ID")
    private String templateId;

    @ApiModelProperty(value = "表名称")
    private String tableName;


    @ApiModelProperty(value = "是否生成代码")
    private boolean generatorCode;

    @ApiModelProperty(value = "代码存放路径")
    private String codePath;

    @ApiModelProperty(value = "是否加载")
    private boolean needLoad;

    @ApiModelProperty(value = "所有字段信息")
    private String fieldsInfo;

    @ApiModelProperty(value = "是否启用默认请求接口")
    private boolean enableWebService;




    public Object getReflation(String key){
        String method = "get".concat(StringUtils.uncapitalize(key));
        try{
            Method methods = getClass().getMethod("method");
            return methods.invoke(this);
        }catch (Exception e){
            throw new RuntimeException("反射异常");
        }
    }


}
