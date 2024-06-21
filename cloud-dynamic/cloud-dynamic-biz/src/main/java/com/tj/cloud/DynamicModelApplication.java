/*
 * Copyright (c) 2003-2021 www.hualongxunda.com/ Inc. All rights reserved.
 * 注意：本内容仅限于深圳华龙讯达信息技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.tj.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/14
 * @Description:
 * @version:1.0
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.tj.cloud.mapper"})
public class DynamicModelApplication {


    public static void main(String[] args){
        SpringApplication.run(DynamicModelApplication.class,args);
    }
}
