
package com.tj.cloud.upms.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tj.cloud.core.exception.ErrorCodes;
import com.tj.cloud.core.http.CloudResult;
import com.tj.cloud.core.utils.MsgUtils;
import com.tj.cloud.security.annotations.Inner;
import com.tj.cloud.upms.entity.User;
import com.tj.cloud.upms.pojo.UserInfoPojo;
import com.tj.cloud.upms.service.IUserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@RestController
@RequestMapping("/app")
@Api("短信登录控制器")
@AllArgsConstructor
public class AppController {

	private final IUserService iUserService;

	/**
	 * 获取指定用户全部信息
	 * @param phone 手机号
	 * @return 用户信息
	 */
	@Inner
	@GetMapping("/info/{phone}")
	public CloudResult<UserInfoPojo> infoByMobile(@PathVariable String phone) {
		User user = iUserService.getOne(Wrappers.<User>query().lambda().eq(User::getMobile, phone));
		if (user == null) {
			return CloudResult.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_USERINFO_EMPTY, phone));
		}
		return CloudResult.ok(iUserService.getUserInfo(user));
	}

}
