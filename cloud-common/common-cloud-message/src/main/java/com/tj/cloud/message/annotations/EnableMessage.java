package com.tj.cloud.message.annotations;

import com.tj.cloud.message.config.MessageAutoConfiguration;
import com.tj.cloud.message.properties.CloudMessageProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * * @Author codingMan_tj * @Date 2024/4/9 15:39 * @version v1.0.0 * @desc
 **/
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableConfigurationProperties(CloudMessageProperties.class)
@Import({ MessageAutoConfiguration.class })
public @interface EnableMessage {

}
