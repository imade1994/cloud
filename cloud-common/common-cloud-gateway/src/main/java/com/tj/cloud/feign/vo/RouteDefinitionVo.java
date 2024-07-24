
package com.tj.cloud.feign.vo;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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






	public static void main(String[] args) throws URISyntaxException {
		RouteDefinitionVo routeDefinition = new RouteDefinitionVo();
		routeDefinition.setRouteName("test");
		routeDefinition.setId("1");
		List<FilterDefinition> filterDefinitions = new ArrayList<>();
		List<PredicateDefinition> predicateDefinitions = new ArrayList<>();
		FilterDefinition filterDefinition1 = new FilterDefinition("ValidateCodeGatewayFilter");
		filterDefinitions.add(filterDefinition1);
		FilterDefinition filterDefinition2 = new FilterDefinition("PasswordDecoderFilter");
		filterDefinitions.add(filterDefinition2);
		PredicateDefinition predicateDefinition = new PredicateDefinition("Path=/auth/**");
		predicateDefinitions.add(predicateDefinition);
		routeDefinition.setPredicates(predicateDefinitions);
		routeDefinition.setFilters(filterDefinitions);
		routeDefinition.setUri(new URI("lb://cloud-auth"));
		routeDefinition.setOrder(0);
		System.out.println(JSONObject.toJSONString(routeDefinition));
	}

}
