package com.tj.cloud.upms.feign;

import com.tj.cloud.core.constant.CommonConstant;
import com.tj.cloud.core.constant.ServiceNameConstants;
import com.tj.cloud.core.http.CloudResult;
import com.tj.cloud.upms.dto.UserDTO;
import com.tj.cloud.upms.pojo.UserInfoPojo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 15:41 * @version v1.0.0 * @desc
 **/
@FeignClient(contextId = "remoteUserFeignService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteUserFeignService {

	/**
	 * 通过用户名查询用户、角色信息
	 * @param username 用户名
	 * @return CloudResult
	 */
	@GetMapping(value = "/user/info/{username}", headers = CommonConstant.HEADER_FROM_IN)
	CloudResult<UserInfoPojo> info(@PathVariable("username") String username);

	/**
	 * 通过手机号码查询用户、角色信息
	 * @param phone 手机号码
	 * @return CloudResult
	 */
	@GetMapping(value = "/app/info/{phone}", headers = CommonConstant.HEADER_FROM_IN)
	CloudResult<UserInfoPojo> infoByMobile(@PathVariable("phone") String phone);

	/**
	 * 根据部门id，查询对应的用户 id 集合
	 * @param deptIds 部门id 集合
	 * @return 用户 id 集合
	 */
	@GetMapping(value = "/user/ids", headers = CommonConstant.HEADER_FROM_IN)
	CloudResult<List<String>> listUserIdByDeptIds(@RequestParam("deptIds") Set<String> deptIds);

}
