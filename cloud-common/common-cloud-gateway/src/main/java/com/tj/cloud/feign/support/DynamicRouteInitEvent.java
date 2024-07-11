
package com.tj.cloud.feign.support;

import org.springframework.context.ApplicationEvent;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/25
 * @Description:
 * @version:1.0
 */
public class DynamicRouteInitEvent extends ApplicationEvent {

	public DynamicRouteInitEvent(Object source) {
		super(source);
	}

}
