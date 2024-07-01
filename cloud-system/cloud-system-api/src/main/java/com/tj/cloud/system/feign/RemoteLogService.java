
package com.tj.cloud.system.feign;

import com.tj.cloud.core.constant.CommonConstant;
import com.tj.cloud.core.constant.ServiceNameConstants;
import com.tj.cloud.core.http.CloudResult;

import com.tj.cloud.system.entity.SysLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteLogService {

	/**
	 * 保存日志
	 * @param sysLog 日志实体
	 * @return success、false
	 */
	@PostMapping(value = "/log/save", headers = CommonConstant.HEADER_FROM_IN)
	CloudResult<Boolean> saveLog(@RequestBody SysLog sysLog);

}
