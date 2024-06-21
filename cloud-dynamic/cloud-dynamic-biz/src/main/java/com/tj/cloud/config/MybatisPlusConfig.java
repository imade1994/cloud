/*
 * Copyright (c) 2003-2021 www.hualongxunda.com/ Inc. All rights reserved.
 * 注意：本内容仅限于深圳华龙讯达信息技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.tj.cloud.config;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/14
 * @Description:
 * @version:1.0
 */
//@Configuration
public class MybatisPlusConfig {
    @Resource
    GlobalConfig globalConfig;

    @Resource
    MybatisPlusProperties mybatisPlusProperties;


    @Bean
    public GlobalConfig globalConfig(){
        return new GlobalConfig();
    }


    @Bean(name = "sqlSessionFactory")
    public MybatisSqlSessionFactoryBean sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) {
        String[] mapperLocations = {"classpath*:com/hlxd/*/*/mapping/*Mapper.xml"};
        if (ArrayUtils.isNotEmpty(mybatisPlusProperties.getMapperLocations())) {
            mapperLocations = ArrayUtils.addAll(mapperLocations, mybatisPlusProperties.getMapperLocations());
        }
        List<Interceptor> plugins = new ArrayList<>();
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(resolveMapperLocations(mapperLocations));
        sqlSessionFactoryBean.setPlugins(plugins.toArray(new Interceptor[0]));
        com.baomidou.mybatisplus.core.MybatisConfiguration configuration = new com.baomidou.mybatisplus.core.MybatisConfiguration();
        //configuration.setDefaultEnumTypeHandler();
        configuration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(configuration);
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setUpdateStrategy(FieldStrategy.NOT_EMPTY);
        globalConfig.setDbConfig(dbConfig);
        // 关闭 logo
        globalConfig.setBanner(Boolean.TRUE);
        // 添加自定义sql注入接口
        sqlSessionFactoryBean.setGlobalConfig(globalConfig);
        return sqlSessionFactoryBean;
    }


    private org.springframework.core.io.Resource[] resolveMapperLocations(String... locations) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<org.springframework.core.io.Resource> resources = new ArrayList<>();
        for (String mapperLocation : locations) {
            try {
                org.springframework.core.io.Resource[] mappers = resourceResolver.getResources(mapperLocation);
                resources.addAll(Arrays.asList(mappers));
            } catch (IOException e) {
                // ignore
            }
        }
        return resources.toArray(new org.springframework.core.io.Resource[0]);
    }


    @Bean("sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") MybatisSqlSessionFactoryBean sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(Objects.requireNonNull(sqlSessionFactory.getObject()));
    }



}
