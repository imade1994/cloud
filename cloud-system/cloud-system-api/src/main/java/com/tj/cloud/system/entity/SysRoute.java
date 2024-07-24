package com.tj.cloud.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tj.cloud.mybatis.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/16
 * @Description:
 * @version:1.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_route")
@Data
public class SysRoute extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 路由名称
     */
    @ApiModelProperty(value = "路由名称")
    private String routeName;

    /**
     * 断言
     */
    @ApiModelProperty(value = "断言")
    private String predicates;

    /**
     * 过滤器
     */
    @ApiModelProperty(value = "过滤器")
    private String filters;

    /**
     * uri
     */
    @ApiModelProperty(value = "请求uri")
    private String uri;

    /**
     * 排序
     * */
    @ApiModelProperty("排序")
    private Integer orderNum;
}
