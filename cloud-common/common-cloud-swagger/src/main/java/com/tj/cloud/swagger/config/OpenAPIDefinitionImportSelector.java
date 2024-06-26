package com.tj.cloud.swagger.config;

import com.tj.cloud.swagger.annatations.EnableCloudSwagger;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Optional;

/**
 * * @Author codingMan_tj * @Date 2024/3/26 15:06 * @version v1.0.0 * @desc
 **/
public class OpenAPIDefinitionImportSelector implements ImportBeanDefinitionRegistrar {

	/**
	 * 注册Bean定义方法
	 * @param metadata 注解元数据
	 * @param registry Bean定义注册器
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		Optional.ofNullable(metadata.getAnnotationAttributes(EnableCloudSwagger.class.getName(), true))
				.map(attrs -> attrs.get("value")).ifPresent(value -> {
					createBeanDefinition(registry, "openAPIMetadataRegister", OpenAPIMetadataRegister.class, value);
					createBeanDefinition(registry, "openAPIDefinition", OpenAPIDefinition.class, value);
				});
	}

	/**
	 * 创建Bean定义
	 * @param registry Bean定义注册器
	 * @param beanName Bean名称
	 * @param beanClass Bean类
	 * @param value Bean属性值
	 */
	private void createBeanDefinition(BeanDefinitionRegistry registry, String beanName, Class<?> beanClass,
			Object value) {
		BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
		beanDefinition.addPropertyValue("path", value);
		registry.registerBeanDefinition(beanName, beanDefinition.getBeanDefinition());
	}

}
