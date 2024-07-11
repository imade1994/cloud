
package com.tj.cloud.mybatis;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.tj.cloud.mybatis.config.MybatisPlusMetaObjectHandler;
import com.tj.cloud.mybatis.plugins.CloudPaginationInnerInterceptor;
import com.tj.cloud.mybatis.plugins.SqlFilterArgumentResolver;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description: mybatis plus 统一配置
 * @version:1.0
 */
@Configuration(proxyBeanMethods = false)
public class MybatisAutoConfiguration implements WebMvcConfigurer {

	/*
	 * @Resource MybatisPlusProperties mybatisPlusProperties;
	 */
	/**
	 * SQL 过滤器避免SQL 注入
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new SqlFilterArgumentResolver());
	}

	/**
	 * 分页插件, 对于单一数据库类型来说,都建议配置该值,避免每次分页都去抓取数据库类型
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new CloudPaginationInnerInterceptor());
		return interceptor;
	}

	/**
	 * 审计字段自动填充
	 * @return {@link MetaObjectHandler}
	 */
	@Bean
	public MybatisPlusMetaObjectHandler mybatisPlusMetaObjectHandler() {
		return new MybatisPlusMetaObjectHandler();
	}

	/*
	 * @Bean(name = "sqlSessionFactory") public MybatisSqlSessionFactoryBean
	 * sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) { String[]
	 * mapperLocations = {"classpath*:mapper/*Mapper.xml"}; if
	 * (ArrayUtils.isNotEmpty(mybatisPlusProperties.getMapperLocations())) {
	 * mapperLocations = ArrayUtils.addAll(mapperLocations,
	 * mybatisPlusProperties.getMapperLocations()); } MybatisSqlSessionFactoryBean
	 * sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
	 * sqlSessionFactoryBean.setDataSource(dataSource);
	 * sqlSessionFactoryBean.setMapperLocations(resolveMapperLocations(mapperLocations));
	 * MybatisConfiguration configuration = mybatisPlusProperties.getConfiguration();
	 * configuration.setDefaultEnumTypeHandler(MybatisEnumTypeHandler.class);
	 * configuration.setMapUnderscoreToCamelCase(true);
	 * sqlSessionFactoryBean.setConfiguration(configuration); GlobalConfig.DbConfig
	 * dbConfig = mybatisPlusProperties.getGlobalConfig().getDbConfig();
	 * dbConfig.setUpdateStrategy(FieldStrategy.NOT_EMPTY); // 添加自定义sql注入接口
	 * sqlSessionFactoryBean.setGlobalConfig(mybatisPlusProperties.getGlobalConfig());
	 * return sqlSessionFactoryBean; }
	 *
	 *
	 * private org.springframework.core.io.Resource[] resolveMapperLocations(String...
	 * locations) { ResourcePatternResolver resourceResolver = new
	 * PathMatchingResourcePatternResolver(); List<org.springframework.core.io.Resource>
	 * resources = new ArrayList<>(); for (String mapperLocation : locations) { try {
	 * org.springframework.core.io.Resource[] mappers =
	 * resourceResolver.getResources(mapperLocation);
	 * resources.addAll(Arrays.asList(mappers)); } catch (IOException e) { // ignore } }
	 * return resources.toArray(new org.springframework.core.io.Resource[0]); }
	 */

	/*
	 * @Bean("sqlSessionTemplate") public SqlSessionTemplate
	 * sqlSessionTemplate(@Qualifier("sqlSessionFactory") MybatisSqlSessionFactoryBean
	 * sqlSessionFactory) throws Exception { return new
	 * SqlSessionTemplate(Objects.requireNonNull(sqlSessionFactory.getObject())); }
	 */

}
