package com.tj.cloud.swagger.annatations;

import com.tj.cloud.swagger.config.OpenAPIDefinitionImportSelector;
import com.tj.cloud.swagger.properties.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * * @Author codingMan_tj * @Date 2024/3/25 14:26 * @version v1.0.0 * @desc swagger 自定义注解
 **/

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(SwaggerProperties.class)
@Import({ OpenAPIDefinitionImportSelector.class })
public @interface EnableCloudSwagger {

}
