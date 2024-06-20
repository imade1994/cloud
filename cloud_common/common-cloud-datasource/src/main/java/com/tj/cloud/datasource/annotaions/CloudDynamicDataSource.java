package com.tj.cloud.datasource.annotaions;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/28 10:51
 * * @version v1.0.0
 * * @desc 多数据源注解
 **/
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({DataSourceAutoConfiguration.class})
public @interface CloudDynamicDataSource {
}
