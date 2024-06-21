package com.tj.cloud.security.login.service;

import cn.hutool.core.util.ArrayUtil;
import com.tj.cloud.core.http.CloudResult;
import com.tj.cloud.core.model.authentication.pojo.UserInfoPojo;
import com.tj.cloud.core.model.authentication.vo.User;
import com.tj.cloud.core.utils.RetOps;
import com.tj.cloud.security.constant.SecurityConstant;
import com.tj.cloud.security.login.entity.LoginUser;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 14:11 * @version v1.0.0 * @desc
 **/
public interface CloudUserDetailsService extends UserDetailsService, Ordered {

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	default boolean support(String clientId, String grantType) {
		return true;
	}

	/**
	 * 排序值 默认取最大的
	 * @return 排序值
	 */
	default int getOrder() {
		return 0;
	}

	/**
	 * 构建userdetails
	 * @param result 用户信息
	 * @return UserDetails
	 */
	default UserDetails getUserDetails(CloudResult<UserInfoPojo> result) {
		UserInfoPojo userInfo = RetOps.of(result).getData().orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

		Set<String> dbAuthsSet = new HashSet<>();

		if (ArrayUtil.isNotEmpty(userInfo.getRoles())) {
			// 获取角色
			Arrays.stream(userInfo.getRoles()).forEach(role -> dbAuthsSet.add(SecurityConstant.ROLE + role));
			// 获取资源
			dbAuthsSet.addAll(Arrays.asList(userInfo.getPermissions()));

		}

		Collection<GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList(dbAuthsSet.toArray(new String[0]));
		User user = userInfo.getUser();

		// 构造security用户
		return new LoginUser(user, authorities);
	}

	/**
	 * 通过用户实体查询
	 * @param pigUser user
	 * @return
	 */
	default UserDetails loadUserByUser(LoginUser loginUser) {
		return this.loadUserByUsername(loginUser.getUsername());
	}

}
