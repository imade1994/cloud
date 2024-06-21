package com.tj.cloud.message.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * * @Author codingMan_tj * @Date 2024/4/9 16:18 * @version v1.0.0 * @desc
 **/
@ConfigurationProperties(prefix = "cloud.message.kafka")
@Data
public class KafkaProperties {

	/**
	 * 服务器地址
	 */
	private String servers;

	private boolean auth;

	private String loginConfig;

	private String securityProtocol;

	private String saslMechanism;

	/**
	 * 生产者列表
	 */
	private List<Producer> produucerList;

	public class Producer {

		/**
		 * 是否启用生产者
		 */
		private String enable;

		/**
		 * 话题名称
		 */
		private String topicName;

		/**
		 * 发送消息 缓冲区大小
		 */
		private String sendBuffer;

		/**
		 * 单挑消息的 最大值
		 */
		private String requestSize;

		/**
		 * ACK机制，默认为1 (0,1,-1)
		 */
		private String ackMode;

		/**
		 * 重试次数
		 */
		private String retry;

		/**
		 * 重试间隔
		 */
		private String retryBackoff;

		/**
		 * 分区配置
		 */
		private String partitionConfig;

		/**
		 * 是否开启消息幂等 默认true
		 */
		private String idempotence;

		public void setAckMode(String ackMode) {
			this.ackMode = ackMode;
		}

		public String getAckMode() {
			return ackMode;
		}

		public String getEnable() {
			return enable;
		}

		public void setEnable(String enable) {
			this.enable = enable;
		}

		public String getTopicName() {
			return topicName;
		}

		public void setTopicName(String topicName) {
			this.topicName = topicName;
		}

		public String getSendBuffer() {
			return sendBuffer;
		}

		public void setSendBuffer(String sendBuffer) {
			this.sendBuffer = sendBuffer;
		}

		public String getRequestSize() {
			return requestSize;
		}

		public void setRequestSize(String requestSize) {
			this.requestSize = requestSize;
		}

		public String getRetry() {
			return retry;
		}

		public void setRetry(String retry) {
			this.retry = retry;
		}

		public String getRetryBackoff() {
			return retryBackoff;
		}

		public void setRetryBackoff(String retryBackoff) {
			this.retryBackoff = retryBackoff;
		}

		public String getPartitionConfig() {
			return partitionConfig;
		}

		public void setPartitionConfig(String partitionConfig) {
			this.partitionConfig = partitionConfig;
		}

		public String getIdempotence() {
			return idempotence;
		}

		public void setIdempotence(String idempotence) {
			this.idempotence = idempotence;
		}

	}

}
