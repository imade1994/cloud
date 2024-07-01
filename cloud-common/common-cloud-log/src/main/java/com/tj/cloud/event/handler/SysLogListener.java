
package com.tj.cloud.event.handler;

import com.tj.cloud.event.SysLogEvent;
import com.tj.cloud.system.entity.SysLog;
import com.tj.cloud.system.feign.RemoteLogService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@Service
public class SysLogListener {

	private final RemoteLogService remoteLogService;

	public SysLogListener(RemoteLogService remoteLogService) {
		this.remoteLogService = remoteLogService;
	}

	@Async
	@Order
	@EventListener(SysLogEvent.class)
	public void saveSysLog(SysLogEvent event) {
		SysLog sysLog = (SysLog) event.getSource();
		remoteLogService.saveLog(sysLog);
	}

}
