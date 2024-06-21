package com.tj.cloud.security.authentication;

import cn.hutool.extra.spring.SpringUtil;
import com.tj.cloud.core.constant.CommonConstant;
import com.tj.cloud.security.login.service.CloudUserDetailsService;
import com.tj.cloud.security.login.entity.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.security.Principal;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 14:05 * @version v1.0.0 * @desc
 **/
public class CustomOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

	private static final Logger log = LoggerFactory.getLogger(OpaqueTokenIntrospector.class);

	private final OAuth2AuthorizationService authorizationService;

	public CustomOpaqueTokenIntrospector(OAuth2AuthorizationService customOpaqueTokenIntrospector) {
		this.authorizationService = customOpaqueTokenIntrospector;
	}

	@Override
	public OAuth2AuthenticatedPrincipal introspect(String token) {
		OAuth2Authorization oldAuthorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
		if (Objects.isNull(oldAuthorization)) {
			throw new InvalidBearerTokenException(token);
		}

		// 客户端模式默认返回
		if (AuthorizationGrantType.CLIENT_CREDENTIALS.equals(oldAuthorization.getAuthorizationGrantType())) {
			return new DefaultOAuth2AuthenticatedPrincipal(oldAuthorization.getPrincipalName(),
					Objects.requireNonNull(oldAuthorization.getAccessToken().getClaims()),
					AuthorityUtils.NO_AUTHORITIES);
		}

		Map<String, CloudUserDetailsService> userDetailsServiceMap = SpringUtil
				.getBeansOfType(CloudUserDetailsService.class);

		Optional<CloudUserDetailsService> optional = userDetailsServiceMap.values().stream()
				.filter(service -> service.support(Objects.requireNonNull(oldAuthorization).getRegisteredClientId(),
						oldAuthorization.getAuthorizationGrantType().getValue()))
				.max(Comparator.comparingInt(Ordered::getOrder));

		UserDetails userDetails = null;
		try {
			Object principal = Objects.requireNonNull(oldAuthorization).getAttributes().get(Principal.class.getName());
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
			Object tokenPrincipal = usernamePasswordAuthenticationToken.getPrincipal();
			userDetails = optional.get().loadUserByUser((LoginUser) tokenPrincipal);
		}
		catch (UsernameNotFoundException notFoundException) {
			log.warn("用户不不存在 {}", notFoundException.getLocalizedMessage());
			throw notFoundException;
		}
		catch (Exception ex) {
			log.error("资源服务器 introspect Token error {}", ex.getLocalizedMessage());
		}

		// 注入扩展属性,方便上下文获取客户端ID
		LoginUser user = (LoginUser) userDetails;
		Objects.requireNonNull(user).getAttributes().put(CommonConstant.CLIENT_ID,
				oldAuthorization.getRegisteredClientId());
		return user;
	}

}
