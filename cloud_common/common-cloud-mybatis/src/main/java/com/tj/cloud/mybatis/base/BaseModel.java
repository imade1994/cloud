package com.tj.cloud.mybatis.base;

import com.tj.cloud.core.utils.ToStringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/29 17:27
 * * @version v1.0.0
 * * @desc
 **/
@ApiModel("框架所有Model都需要继承的model")
@Data
public class BaseModel extends ToStringUtils implements IBaseModel, Serializable {


    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    protected String id;

    @ApiModelProperty("创建时间")
    protected LocalDateTime createTime;

    @ApiModelProperty("创建人ID")
    protected String createBy;


    @ApiModelProperty("更新时间")
    protected LocalDateTime updateTime;

    @ApiModelProperty("更新人ID")
    protected String updateBy;

}
