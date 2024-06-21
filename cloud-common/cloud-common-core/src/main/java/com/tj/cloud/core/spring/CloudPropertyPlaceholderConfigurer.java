package com.tj.cloud.core.spring;

import com.tj.cloud.core.abs.IProperty;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Map;
import java.util.Properties;

/**
 * * @Author codingMan_tj * @Date 2024/3/26 12:02 * @version v1.0.0 * @desc 自定义环境配置
 * 配置文件加解密
 **/
public class CloudPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer
		implements IProperty, EnvironmentAware {

	private Environment environment;

	@Override
	protected void convertProperties(Properties props) {
		ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
		Properties newProperties = new Properties();
		for (Map.Entry<Object, Object> propEntry : props.entrySet()) {
			if (!configurableEnvironment.containsProperty(String.valueOf(propEntry.getKey()))) {
				newProperties.put(propEntry.getKey(), propEntry.getValue());
			}
		}
		configurableEnvironment.getPropertySources().addLast(new PropertiesPropertySource("cloud", newProperties));
		super.convertProperties(props);
	}

	/**
	 * 根据建获取属性中的值。
	 */
	@Override
	public String getValue(String key) {
		return environment.getProperty(key);
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

}
