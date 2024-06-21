package com.tj.cloud.auth.support.core;

import com.tj.cloud.security.constant.SecurityConstant;
import com.tj.cloud.security.login.entity.LoginUser;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * * @Author codingMan_tj
 * * @Date 2024/4/7 10:22
 * * @version v1.0.0
 * * @desc
 **/
public class CustomeOAuth2TokenCustomizer implements OAuth2TokenCustomizer<OAuth2TokenClaimsContext> {
    /**
     * Customize the OAuth 2.0 Token attributes.
     * @param context the context containing the OAuth 2.0 Token attributes
     */
    @Override
    public void customize(OAuth2TokenClaimsContext context) {
        OAuth2TokenClaimsSet.Builder claims = context.getClaims();
        claims.claim(SecurityConstant.DETAILS_LICENSE, SecurityConstant.PROJECT_LICENSE);
        String clientId = context.getAuthorizationGrant().getName();
        claims.claim(SecurityConstant.CLIENT_ID, clientId);
        // 客户端模式不返回具体用户信息
        if (SecurityConstant.CLIENT_CREDENTIALS.equals(context.getAuthorizationGrantType().getValue())) {
            return;
        }

        LoginUser loginUser = (LoginUser) context.getPrincipal().getPrincipal();
        claims.claim(SecurityConstant.DETAILS_USER, loginUser);
        claims.claim(SecurityConstant.DETAILS_USER_ID, loginUser.getId());
        claims.claim(SecurityConstant.USERNAME, loginUser.getUsername());
    }
}
