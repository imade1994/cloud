package com.tj.cloud.auth.support.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * * @Author codingMan_tj * @Date 2024/4/9 15:02 * @version v1.0.0 * @desc
 **/
@Component
public class CloudLogoutSuccessEventHandler implements ApplicationListener<LogoutSuccessEvent> {

	private static final Logger logger = LoggerFactory.getLogger(CloudLogoutSuccessEventHandler.class);

	@Override
	public void onApplicationEvent(LogoutSuccessEvent event) {
		Authentication authentication = (Authentication) event.getSource();
		if (authentication instanceof PreAuthenticatedAuthenticationToken) {
			handle(authentication);
		}
	}

	/**
	 * 处理退出成功方法
	 * <p>
	 * 获取到登录的authentication 对象
	 * @param authentication 登录对象
	 */
	public void handle(Authentication authentication) {
		// todo gateway 记录退出日志
	}

}
