package com.tj.cloud.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tj.cloud.mybatis.base.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 17:03 * @version v1.0.0 * @desc
 **/
@EqualsAndHashCode(callSuper = true)
@TableName("t_upms_oauth_client_details")
@Data
public class SysOauthClientDetails extends BaseModel {

	/**
	 * 客户端密钥
	 */
	@NotBlank(message = "client_secret 不能为空")
	@Schema(description = "客户端密钥")
	private String clientSecret;

	/**
	 * 资源ID
	 */
	@Schema(description = "资源id列表")
	private String resourceIds;

	/**
	 * 作用域
	 */
	@NotBlank(message = "scope 不能为空")
	@Schema(description = "作用域")
	private String scope;

	/**
	 * 授权方式[A,B,C]
	 */
	@Schema(description = "授权方式")
	private String[] authorizedGrantTypes;

	/**
	 * 回调地址
	 */
	@Schema(description = "回调地址")
	private String webServerRedirectUri;

	/**
	 * 权限
	 */
	@Schema(description = "权限列表")
	private String authorities;

	/**
	 * 请求令牌有效时间
	 */
	@Schema(description = "请求令牌有效时间")
	private Integer accessTokenValidity;

	/**
	 * 刷新令牌有效时间
	 */
	@Schema(description = "刷新令牌有效时间")
	private Integer refreshTokenValidity;

	/**
	 * 扩展信息
	 */
	@Schema(description = "扩展信息")
	private String additionalInformation;

	/**
	 * 是否自动放行
	 */
	@Schema(description = "是否自动放行")
	private String autoApprove;

	/**
	 * 删除标记
	 */
	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "删除标记,1:已删除,0:正常")
	private String delFlag;

}
