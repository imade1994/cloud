package com.tj.cloud.message.support.pulsar;

import com.tj.cloud.core.abs.IMessage;
import com.tj.cloud.core.abs.IMessageProducer;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * * @Author codingMan_tj * @Date 2024/4/10 17:03 * @version v1.0.0 * @desc
 **/
public class PulsarMessageProducer implements IMessageProducer {

	private static final Logger logger = LoggerFactory.getLogger(PulsarMessageProducer.class);

	private final Map<String, Producer<byte[]>> producerMap;

	public PulsarMessageProducer(Map<String, Producer<byte[]>> producerMap) {
		this.producerMap = producerMap;
	}

	@Override
	public void sendMessage(IMessage message) {
		try {
			producerMap.get(message.getType()).send(message.getData().toString().getBytes(StandardCharsets.UTF_8));
		}
		catch (PulsarClientException e) {
			logger.error("发送消息失败");
		}
	}

	@Override
	public void sendMessages(List<IMessage> messages) {
		for (IMessage message : messages) {
			try {
				producerMap.get(message.getType()).send(message.getData().toString().getBytes(StandardCharsets.UTF_8));
			}
			catch (PulsarClientException e) {
				logger.error("发送消息失败");
			}
		}
	}

}
