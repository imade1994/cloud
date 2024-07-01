package com.tj.cloud.security.login;

import com.tj.cloud.core.abs.ICache;
import com.tj.cloud.core.constant.CommonConstant;
import com.tj.cloud.core.http.CloudResult;
import com.tj.cloud.upms.dto.UserDTO;
import com.tj.cloud.upms.pojo.UserInfoPojo;
import com.tj.cloud.security.constant.SecurityConstant;
import com.tj.cloud.security.login.entity.LoginUser;
import com.tj.cloud.security.login.service.CloudUserDetailsService;
import com.tj.cloud.upms.feign.RemoteUserFeignService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 16:43 * @version v1.0.0 * @desc
 **/
@RequiredArgsConstructor
public class CloudAppUserDetailsServiceImpl implements CloudUserDetailsService {

	private final RemoteUserFeignService remoteUserService;

	private final ICache<UserDetails> iCache;

	/**
	 * 手机号登录
	 * @param phone 手机号
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String phone) {
		UserDetails loginUser = iCache.getByKey(SecurityConstant.SECURITY_CACHE_USER_DETAILS, phone);
		if (!ObjectUtils.isEmpty(phone)) {
			return loginUser;
		}
		CloudResult<UserInfoPojo> result = remoteUserService.infoByMobile(phone);
		UserDetails userDetails = getUserDetails(result);
		iCache.addToRegion(SecurityConstant.SECURITY_CACHE_USER_DETAILS, phone, userDetails);
		return userDetails;
	}

	/**
	 * check-token 使用
	 * @param loginUser user
	 * @return
	 */
	@Override
	public UserDetails loadUserByUser(LoginUser loginUser) {
		return this.loadUserByUsername(String.valueOf(loginUser.getMobile()));
	}

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	@Override
	public boolean support(String clientId, String grantType) {
		return CommonConstant.MOBILE.equals(grantType);
	}

}
