package com.tj.cloud.message.support.kafka;

import cn.hutool.core.lang.UUID;
import com.tj.cloud.core.abs.IMessage;
import com.tj.cloud.core.abs.IMessageProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;
import java.util.Map;

/**
 * * @Author codingMan_tj * @Date 2024/4/9 15:55 * @version v1.0.0 * @desc
 **/

public class KafkaMessageProducer implements IMessageProducer {

	private final Map<String, KafkaProducer<String, Object>> producerMap;

	public KafkaMessageProducer(Map<String, KafkaProducer<String, Object>> producerMap) {
		this.producerMap = producerMap;
	}

	@Override
	public void sendMessage(IMessage message) {
		producerMap.get(message.getType())
				.send(new ProducerRecord<>(message.getType(), UUID.randomUUID().toString(), message.getData()));
	}

	@Override
	public void sendMessages(List<IMessage> messages) {
		for (IMessage message : messages) {
			producerMap.get(message.getType())
					.send(new ProducerRecord<>(message.getType(), UUID.randomUUID().toString(), message.getData()));
		}
	}

}
