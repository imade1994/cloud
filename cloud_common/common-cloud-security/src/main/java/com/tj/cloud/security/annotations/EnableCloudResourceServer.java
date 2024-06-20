package com.tj.cloud.security.annotations;

import com.tj.cloud.security.authentication.CloudResourceServerAutoConfiguration;
import com.tj.cloud.security.authentication.CloudResourceServerConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.lang.annotation.*;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/29 10:52
 * * @version v1.0.0
 * * @desc
 **/
@Documented
@Inherited
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ CloudResourceServerAutoConfiguration.class, CloudResourceServerConfiguration.class })
public @interface EnableCloudResourceServer {
}
