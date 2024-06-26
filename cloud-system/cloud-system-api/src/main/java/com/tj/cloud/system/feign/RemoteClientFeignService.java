package com.tj.cloud.system.feign;

import com.tj.cloud.core.constant.CommonConstant;
import com.tj.cloud.core.constant.ServiceNameConstants;
import com.tj.cloud.core.http.CloudResult;
import com.tj.cloud.system.entity.SysOauthClientDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 15:41 * @version v1.0.0 * @desc
 **/
@FeignClient(contextId = "remoteClientFeignService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteClientFeignService {

	/**
	 * 通过clientId 查询客户端信息
	 * @param clientId 用户名
	 * @return CloudResult
	 */
	@GetMapping(value = "/client/getClientDetailsById/{clientId}", headers = CommonConstant.HEADER_FROM_IN)
	CloudResult<SysOauthClientDetails> getClientDetailsById(@PathVariable("clientId") String clientId);

	/**
	 * 查询全部客户端
	 * @return CloudResult
	 */
	@GetMapping(value = "/client/list", headers = CommonConstant.HEADER_FROM_IN)
	CloudResult<List<SysOauthClientDetails>> listClientDetails();

}
