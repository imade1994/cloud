package com.tj.cloud.upms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tj.cloud.core.model.authentication.IUser;
import com.tj.cloud.mybatis.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 11:50 * @version v1.0.0 * @desc 登陆用户实体
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_upms_user")
public class User extends BaseModel implements IUser, Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("姓名")
	private String name;

	@ApiModelProperty("账号")
	private String account;

	@ApiModelProperty("密码")
	private String password;

	@ApiModelProperty("邮箱")
	private String email;

	@ApiModelProperty("微信ID")
	private String wechat;

	@ApiModelProperty("三方ID")
	private String openId;

	@ApiModelProperty("电话")
	private Integer mobile;

	@ApiModelProperty("状态")
	private Integer status;

	@ApiModelProperty("租户ID")
	private String tenantId;

	@ApiModelProperty("部门ID")
	private String deptId;

	@ApiModelProperty("组织ID")
	private String orgId;

	@ApiModelProperty("过期时间")
	private LocalDateTime expireDate;

	@ApiModelProperty("是否自动刷新")
	private Integer autoRenewal;

}
