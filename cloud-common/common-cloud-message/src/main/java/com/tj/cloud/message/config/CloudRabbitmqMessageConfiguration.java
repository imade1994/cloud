package com.tj.cloud.message.config;

import com.tj.cloud.message.properties.RabbitmqProperties;
import com.tj.cloud.message.support.rabbitmq.RabbitmqMessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * * @Author codingMan_tj * @Date 2024/4/9 16:16 * @version v1.0.0 * @desc
 **/
@EnableConfigurationProperties(RabbitmqProperties.class)
public class CloudRabbitmqMessageConfiguration {

	private static final Logger log = LoggerFactory.getLogger(CloudRabbitmqMessageConfiguration.class);

	@Resource
	RabbitmqProperties rabbitmqProperties;

	@Bean
	CachingConnectionFactory connectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setHost(rabbitmqProperties.getUrl());
		cachingConnectionFactory.setPort(rabbitmqProperties.getPort());
		cachingConnectionFactory.setUsername(rabbitmqProperties.getUsername());
		cachingConnectionFactory.setPassword(rabbitmqProperties.getPassword());
		cachingConnectionFactory.setVirtualHost(rabbitmqProperties.getVirtualHost());
		cachingConnectionFactory.setPublisherReturns(rabbitmqProperties.isPublisherReturns());
		// cachingConnectionFactory.setPublisherConfirms(rabbitmqProperties.isPublisherConfirms());
		return cachingConnectionFactory;
	}

	@Bean
	public RabbitTemplate rabbitTemplateMap(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		// template.setMandatory(rabbitMQPropertiesDetails.getProducer().getMandatory());
		template.setMessageConverter(new Jackson2JsonMessageConverter());
		template.setConfirmCallback((correlationData, ack, cause) -> {
			if (ack) {
				log.info("message send succeed:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
			}
			else {
				log.info("message send failed:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
			}
		});
		return template;
	}

	@Bean
	RabbitmqMessageProducer rabbitmqMessageProducer(@Qualifier RabbitTemplate rabbitTemplate) {
		return new RabbitmqMessageProducer(rabbitTemplate);
	}

}
