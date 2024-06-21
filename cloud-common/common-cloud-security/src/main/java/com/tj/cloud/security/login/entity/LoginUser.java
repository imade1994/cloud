package com.tj.cloud.security.login.entity;

import com.tj.cloud.core.model.authentication.vo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 11:58 * @version v1.0.0 * @desc security 用户类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser extends User implements Serializable, UserDetails, OAuth2AuthenticatedPrincipal {

	private static final long serialVersionUID = 4962121675615445357L;

	private Collection<? extends GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

	private final Map<String, Object> attributes = new HashMap<>();

	public LoginUser(User user, Collection<GrantedAuthority> authorities) {
		this.setRoles(authorities);
		this.setId(user.getId());
		this.setAccount(user.getAccount());
		this.setEmail(user.getEmail());
		this.setName(user.getName());
		this.setMobile(user.getMobile());
		this.setAutoRenewal(user.getAutoRenewal());
		this.setDeptId(user.getDeptId());
		this.setPassword(user.getPassword());
		this.setWechat(user.getWechat());
		this.setTenantId(user.getTenantId());
		this.setOpenId(user.getOpenId());
		this.setExpireDate(user.getExpireDate());
		this.setStatus(user.getStatus());
	}

	/**
	 * 设置角色。
	 * @param roles
	 */
	public void setAuthorities(Collection<? extends GrantedAuthority> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	/**
	 * Get the OAuth 2.0 token attributes
	 * @return the OAuth 2.0 token attributes
	 */
	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public String getUsername() {
		return this.getAccount();
	}

	@Override
	public boolean isAccountNonExpired() {
		return LocalDateTime.now().isBefore(this.getExpireDate());
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		int status = this.getStatus();
		return status == 1;
	}

}
