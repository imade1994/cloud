package com.tj.cloud.message.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * * @Author codingMan_tj * @Date 2024/4/9 16:20 * @version v1.0.0 * @desc
 **/
@ConfigurationProperties(prefix = "cloud.message.rabbitmq")
@Data
public class RabbitmqProperties {

	private String url;

	private Integer port;

	private String username;

	private String password;

	private String virtualHost;

	private int channelCacheSize;

	private int channelCheckoutTimeout;

	private boolean publisherReturns;

	private boolean publisherConfirms;

	private List<Producer> producerList;

	public class Producer {

		private String exchange;

		private String ruteKey;

		private String queue;

		public String getExchange() {
			return exchange;
		}

		public void setExchange(String exchange) {
			this.exchange = exchange;
		}

		public String getRuteKey() {
			return ruteKey;
		}

		public void setRuteKey(String ruteKey) {
			this.ruteKey = ruteKey;
		}

		public String getQueue() {
			return queue;
		}

		public void setQueue(String queue) {
			this.queue = queue;
		}

	}

}
