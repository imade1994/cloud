
package com.tj.cloud.event;

import com.tj.cloud.system.entity.SysLog;
import org.springframework.context.ApplicationEvent;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
public class SysLogEvent extends ApplicationEvent {

	public SysLogEvent(SysLog source) {
		super(source);
	}

}
