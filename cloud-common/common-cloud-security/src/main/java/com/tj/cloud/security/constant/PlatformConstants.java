package com.tj.cloud.security.constant;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 10:56 * @version v1.0.0 * @desc
 **/
public class PlatformConstants {

	/**
	 * 超级用户
	 */
	private final static String ROLE_SUPER = "ROLE_SUPER";

	/**
	 * 公共角色
	 */
	private final static String ROLE_PUBLIC = "ROLE_PUBLIC";

	/**
	 * 匿名用户
	 */
	private final static String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

	public final static GrantedAuthority ROLE_GRANT_SUPER = new SimpleGrantedAuthority(ROLE_SUPER);

	public final static ConfigAttribute ROLE_CONFIG_PUBLIC = new SecurityConfig(ROLE_PUBLIC);

	public final static ConfigAttribute ROLE_CONFIG_ANONYMOUS = new SecurityConfig(ROLE_ANONYMOUS);

}
