
package com.tj.cloud.system.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tj.cloud.mybatis.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName()
public class SysLog extends BaseModel {

	/**
	 * 日志类型
	 */
	@NotBlank(message = "日志类型不能为空")
	@ApiModelProperty("日志类型")
	private String type;

	/**
	 * 日志标题
	 */
	@NotBlank(message = "日志标题不能为空")
	@ApiModelProperty("日志标题")
	private String title;

	/**
	 * 操作IP地址
	 */

	@ApiModelProperty("操作ip地址")
	private String remoteAddr;

	/**
	 * 用户浏览器
	 */

	@ApiModelProperty("用户代理")
	private String userAgent;

	/**
	 * 请求URI
	 */

	@ApiModelProperty("请求uri")
	private String requestUri;

	/**
	 * 操作方式
	 */

	@ApiModelProperty("操作方式")
	private String method;

	/**
	 * 操作提交的数据
	 */
	@ApiModelProperty("数据")
	private String params;

	/**
	 * 执行时间
	 */
	@ApiModelProperty("方法执行时间")
	private Long time;

	/**
	 * 异常信息
	 */
	@ApiModelProperty("异常信息")
	private String exception;

	/**
	 * 服务ID
	 */
	@ApiModelProperty("应用标识")
	private String serviceId;

	/**
	 * 删除标记
	 */
	@TableLogic
	@ApiModelProperty("逻辑删除标记")
	private String delFlag;

}
