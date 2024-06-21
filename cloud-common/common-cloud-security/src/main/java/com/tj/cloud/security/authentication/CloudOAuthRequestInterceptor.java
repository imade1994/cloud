package com.tj.cloud.security.authentication;

import cn.hutool.core.collection.CollUtil;
import com.tj.cloud.core.constant.CommonConstant;
import com.tj.cloud.core.utils.WebUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 16:08 * @version v1.0.0 * @desc
 **/
@RequiredArgsConstructor
public class CloudOAuthRequestInterceptor implements RequestInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(CloudOAuthRequestInterceptor.class);

	private final BearerTokenResolver tokenResolver;

	/**
	 * Create a template with the header of provided name and extracted extract </br>
	 *
	 * 1. 如果使用 非web 请求，header 区别 </br>
	 *
	 * 2. 根据authentication 还原请求token
	 * @param template
	 */
	@Override
	public void apply(RequestTemplate template) {
		Collection<String> fromHeader = template.headers().get(CommonConstant.FROM);
		// 带from 请求直接跳过
		if (CollUtil.isNotEmpty(fromHeader) && fromHeader.contains(CommonConstant.FROM_IN)) {
			return;
		}

		// 非web 请求直接跳过
		if (!WebUtils.getRequest().isPresent()) {
			return;
		}
		HttpServletRequest request = WebUtils.getRequest().get();
		// 避免请求参数的 query token 无法传递
		String token = tokenResolver.resolve(request);
		if (StringUtils.isBlank(token)) {
			return;
		}
		template.header(HttpHeaders.AUTHORIZATION,
				String.format("%s %s", OAuth2AccessToken.TokenType.BEARER.getValue(), token));

	}

}
