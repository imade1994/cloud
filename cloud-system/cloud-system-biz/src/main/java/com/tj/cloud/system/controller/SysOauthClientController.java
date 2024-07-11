
package com.tj.cloud.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tj.cloud.core.http.CloudResult;
import com.tj.cloud.system.entity.SysOauthClientDetails;
import com.tj.cloud.system.service.ISysOauthClientDetailsService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@Api("客户端管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysOauthClientController {

	@Resource
	ISysOauthClientDetailsService sysOauthClientDetailsService;

	/**
	 * 通过ID查询
	 * @param clientId 客户端id
	 * @return SysOauthClientDetails
	 */
	@GetMapping("/getClientDetailsById/{clientId}")
	public CloudResult<List<SysOauthClientDetails>> getByClientId(@PathVariable String clientId) {
		return CloudResult.ok(sysOauthClientDetailsService
				.list(Wrappers.<SysOauthClientDetails>lambdaQuery().eq(SysOauthClientDetails::getId, clientId)));
	}

}
