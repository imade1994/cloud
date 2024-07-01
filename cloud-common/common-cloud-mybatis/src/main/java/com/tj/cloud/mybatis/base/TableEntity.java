
package com.tj.cloud.mybatis.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/18
 * @Description:
 * @version:1.0
 */
@Data
public class TableEntity implements Serializable {

	@ApiModelProperty(value = "表 属性名")
	private String name;

	@ApiModelProperty(value = "表 属性类型")
	private String type;

	@ApiModelProperty(value = "表 属性备注")
	private String mark;

	@ApiModelProperty(value = "表 属性长度 默认0")
	private int length;

}
