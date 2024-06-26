package com.tj.cloud.auth.config;

import com.tj.cloud.auth.support.CustomOAuth2AccessTokenGenerator;
import com.tj.cloud.auth.support.core.CloudDaoAuthenticationProvider;
import com.tj.cloud.auth.support.core.CustomeOAuth2TokenCustomizer;
import com.tj.cloud.auth.support.core.FormIdentityLoginConfigurer;
import com.tj.cloud.auth.support.handler.CloudAuthenticationFailureEventHandler;
import com.tj.cloud.auth.support.handler.CloudAuthenticationSuccessEventHandler;
import com.tj.cloud.auth.support.password.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import com.tj.cloud.auth.support.password.OAuth2ResourceOwnerPasswordAuthenticationProvider;
import com.tj.cloud.auth.support.sms.OAuth2ResourceOwnerSmsAuthenticationConverter;
import com.tj.cloud.auth.support.sms.OAuth2ResourceOwnerSmsAuthenticationProvider;
import com.tj.cloud.security.constant.SecurityConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.DelegatingAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeRequestAuthenticationConverter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;

import java.util.Arrays;

/**
 * * @Author codingMan_tj * @Date 2024/4/7 9:56 * @version v1.0.0 * @desc
 **/
@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfiguration {

	private final OAuth2AuthorizationService authorizationService;

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {

		// OAuth 2.1 默认配置
		// 缺省配置：authorizeRequests.anyRequest().authenticated()、
		// csrf.ignoringRequestMatchers(endpointsMatcher) 等等
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

		// 使用 HttpSecurity 获取 OAuth 2.1 配置中的 OAuth2AuthorizationServerConfigurer 对象
		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = http
				.getConfigurer(OAuth2AuthorizationServerConfigurer.class);

		authorizationServerConfigurer.tokenEndpoint((tokenEndpoint) -> {// 个性化认证授权端点
			tokenEndpoint.accessTokenRequestConverter(accessTokenRequestConverter()) // 注入自定义的授权认证Converter
					.accessTokenResponseHandler(new CloudAuthenticationSuccessEventHandler()) // 登录成功处理器
					.errorResponseHandler(new CloudAuthenticationFailureEventHandler());// 登录失败处理器
		}).clientAuthentication(oAuth2ClientAuthenticationConfigurer -> // 个性化客户端认证
		oAuth2ClientAuthenticationConfigurer.errorResponseHandler(new CloudAuthenticationFailureEventHandler()))// 处理客户端认证异常
				.authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint// 授权码端点个性化confirm页面
						.consentPage(SecurityConstant.CUSTOM_CONSENT_PAGE_URI));

		DefaultSecurityFilterChain securityFilterChain = authorizationServerConfigurer
				.authorizationService(authorizationService)// redis存储token的实现
				.authorizationServerSettings(
						AuthorizationServerSettings.builder().issuer(SecurityConstant.PROJECT_LICENSE).build())
				// 授权码登录的登录页个性化
				.and().apply(new FormIdentityLoginConfigurer()).and().build();

		// 注入自定义授权模式实现
		addCustomOAuth2GrantAuthenticationProvider(http);
		return securityFilterChain;
	}

	/**
	 * 令牌生成规则实现 </br>
	 * client:username:uuid
	 * @return OAuth2TokenGenerator
	 */
	@Bean
	public OAuth2TokenGenerator oAuth2TokenGenerator() {
		CustomOAuth2AccessTokenGenerator accessTokenGenerator = new CustomOAuth2AccessTokenGenerator();
		// 注入Token 增加关联用户信息
		accessTokenGenerator.setAccessTokenCustomizer(new CustomeOAuth2TokenCustomizer());
		return new DelegatingOAuth2TokenGenerator(accessTokenGenerator, new OAuth2RefreshTokenGenerator());
	}

	/**
	 * request -> xToken 注入请求转换器
	 * @return DelegatingAuthenticationConverter
	 */
	private AuthenticationConverter accessTokenRequestConverter() {
		return new DelegatingAuthenticationConverter(
				Arrays.asList(new OAuth2ResourceOwnerPasswordAuthenticationConverter(),
						new OAuth2ResourceOwnerSmsAuthenticationConverter(),
						new OAuth2AuthorizationCodeRequestAuthenticationConverter()));
	}

	/**
	 * 注入授权模式实现提供方
	 * <p>
	 * 1. 密码模式 </br>
	 * 2. 短信登录 </br>
	 */
	@SuppressWarnings("unchecked")
	private void addCustomOAuth2GrantAuthenticationProvider(HttpSecurity http) {
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);

		OAuth2ResourceOwnerPasswordAuthenticationProvider resourceOwnerPasswordAuthenticationProvider = new OAuth2ResourceOwnerPasswordAuthenticationProvider(
				authenticationManager, authorizationService, oAuth2TokenGenerator());

		OAuth2ResourceOwnerSmsAuthenticationProvider resourceOwnerSmsAuthenticationProvider = new OAuth2ResourceOwnerSmsAuthenticationProvider(
				authenticationManager, authorizationService, oAuth2TokenGenerator());

		// 处理 UsernamePasswordAuthenticationToken
		http.authenticationProvider(new CloudDaoAuthenticationProvider());
		// 处理 OAuth2ResourceOwnerPasswordAuthenticationToken
		http.authenticationProvider(resourceOwnerPasswordAuthenticationProvider);
		// 处理 OAuth2ResourceOwnerSmsAuthenticationToken
		http.authenticationProvider(resourceOwnerSmsAuthenticationProvider);
	}

}
