package com.tj.cloud.message.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * * @Author codingMan_tj * @Date 2024/4/9 16:20 * @version v1.0.0 * @desc
 **/
@ConfigurationProperties(prefix = "cloud.message.pulsar")
@Data
public class PulsarProperties {

	private String serviceUrl;

	private String token;

	private String tlsFile;

	private int operationTimeOut;

	private int ioThreads;

	private int listenerThreads;

	private List<Producer> producerList;

	public class Producer {

		@Getter
		@Setter
		private String topicName;

		@Getter
		@Setter
		private String producerName;

	}

}
