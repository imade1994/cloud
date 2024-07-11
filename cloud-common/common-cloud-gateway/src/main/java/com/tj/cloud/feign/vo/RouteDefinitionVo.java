
package com.tj.cloud.feign.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.io.Serializable;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/24
 * @Description:
 * @version:1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RouteDefinitionVo extends RouteDefinition implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 路由名称
	 */
	private String routeName;

}
