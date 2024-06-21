package com.tj.cloud.auth.support.core;

import com.tj.cloud.auth.support.handler.FormAuthenticationFailureHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * * @Author codingMan_tj
 * * @Date 2024/4/7 10:25
 * * @version v1.0.0
 * * @desc
 **/
public class FormIdentityLoginConfigurer extends AbstractHttpConfigurer<FormIdentityLoginConfigurer, HttpSecurity> {

    @Override
    public void init(HttpSecurity http) throws Exception {
        http.formLogin(formLogin -> {
                    formLogin.loginPage("/token/login");
                    formLogin.loginProcessingUrl("/token/form");
                    formLogin.failureHandler(new FormAuthenticationFailureHandler());

                })
                .logout(logout -> logout.logoutSuccessHandler(new SsoLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)) // SSO登出成功处理

                .csrf(AbstractHttpConfigurer::disable);
    }
}
