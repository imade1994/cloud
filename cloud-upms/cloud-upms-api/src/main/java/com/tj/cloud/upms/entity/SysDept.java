
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
@TableName("t_upms_sys_dept")
@EqualsAndHashCode(callSuper = true)
@ApiModel("岗位信息表")
public class SysDept extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 部门名称
	 */
	@NotBlank(message = "部门名称不能为空")
	@ApiModelProperty("部门名称")
	private String name;

	/**
	 * 排序
	 */
	@NotNull(message = "部门排序值不能为空")
	@ApiModelProperty("排序值")
	private Integer sortOrder;

	/**
	 * 父级部门id
	 */
	@ApiModelProperty("父级部门id")
	private Long parentId;

	/**
	 * 是否删除 -1：已删除 0：正常
	 */
	@TableLogic
	private String delFlag;

}
