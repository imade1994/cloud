
package com.tj.cloud.upms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tj.cloud.mybatis.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@Data
@TableName("t_upms_sys_user_role")
public class SysUserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@ApiModelProperty("用户id")
	private String userId;

	/**
	 * 角色ID
	 */
	@ApiModelProperty("角色id")
	private String roleId;

}
