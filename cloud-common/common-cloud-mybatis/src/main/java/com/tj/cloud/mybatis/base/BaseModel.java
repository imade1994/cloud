package com.tj.cloud.mybatis.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.tj.cloud.core.utils.ToStringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 17:27 * @version v1.0.0 * @desc
 **/
@EqualsAndHashCode(callSuper = true)
@ApiModel("框架所有Model都需要继承的model")
@Data
public class BaseModel extends ToStringUtils implements IBaseModel, Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键id")
	protected String id;

	@ApiModelProperty(value = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	protected LocalDateTime createTime;

	@ApiModelProperty("创建人ID")
	@TableField(fill = FieldFill.INSERT)
	protected String createBy;

	@ApiModelProperty(value = "创建人")
	@TableField(fill = FieldFill.INSERT)
	protected LocalDateTime updateTime;

	@ApiModelProperty(value = "更新人")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	protected String updateBy;

}
