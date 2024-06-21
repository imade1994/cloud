package com.tj.cloud.security.login;

import com.tj.cloud.core.abs.ICache;
import com.tj.cloud.core.constant.CommonConstant;
import com.tj.cloud.core.http.CloudResult;
import com.tj.cloud.core.model.authentication.dto.UserDTO;
import com.tj.cloud.core.model.authentication.pojo.UserInfoPojo;
import com.tj.cloud.security.constant.SecurityConstant;
import com.tj.cloud.security.login.service.CloudUserDetailsService;
import com.tj.cloud.upms.feign.RemoteUserFeignService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 15:21 * @version v1.0.0 * @desc
 **/
@Primary
@RequiredArgsConstructor
public class CloudUserDetailsServiceImpl implements CloudUserDetailsService {

	private final RemoteUserFeignService remoteUserService;

	private final ICache<UserDetails> iCache;

	/**
	 * 用户名密码登录
	 * @param username 用户名
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {
		UserDetails loginUser = iCache.getByKey(SecurityConstant.SECURITY_CACHE_USER_DETAILS, username);
		if (!ObjectUtils.isEmpty(loginUser)) {
			return loginUser;
		}
		UserDTO userDTO = new UserDTO();
		userDTO.setAccount(username);
		CloudResult<UserInfoPojo> result = remoteUserService.info(userDTO, CommonConstant.FROM_IN);
		UserDetails userDetails = getUserDetails(result);
		iCache.addToRegion(SecurityConstant.SECURITY_CACHE_USER_DETAILS, username, userDetails);
		return userDetails;
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
