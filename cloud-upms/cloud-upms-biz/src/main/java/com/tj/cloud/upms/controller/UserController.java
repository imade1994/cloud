
package com.tj.cloud.upms.controller;

import cn.hutool.core.util.ObjectUtil;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@RestController
@RequestMapping("/user")
@Api("用户控制器")
@AllArgsConstructor
public class UserController {

	private final IUserService userService;

	@Inner
	@GetMapping("info/{username}")
	public CloudResult<UserInfoPojo> info(@PathVariable String username) {
		User user = userService.getOne(Wrappers.lambdaQuery(User.class).ge(User::getAccount, username));
		if (ObjectUtil.isNotNull(user)) {
			return CloudResult.ok(userService.getUserInfo(user));
		}
		else {
			return CloudResult.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_USERINFO_EMPTY, username));
		}
	}

	/**
	 * 根据部门id，查询对应的用户 id 集合
	 * @param deptIds 部门id 集合
	 * @return 用户 id 集合
	 */
	@Inner
	@GetMapping("/ids")
	public CloudResult<List<String>> listUserIdByDeptIds(@RequestParam("deptIds") Set<String> deptIds) {
		return CloudResult.ok(userService.listUserIdByDeptIds(deptIds));
	}

}
