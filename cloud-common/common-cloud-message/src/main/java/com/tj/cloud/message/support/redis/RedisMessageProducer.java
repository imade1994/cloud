package com.tj.cloud.message.support.redis;

import com.tj.cloud.core.abs.IMessage;
import com.tj.cloud.core.abs.IMessageProducer;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * * @Author codingMan_tj * @Date 2024/4/16 14:30 * @version v1.0.0 * @desc
 **/
public class RedisMessageProducer implements IMessageProducer {

	private final RedisTemplate<Object, Object> redisTemplate;

	public RedisMessageProducer(RedisTemplate<Object, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void sendMessage(IMessage message) {
		redisTemplate.boundListOps(message.getType()).rightPush(message.getData());
	}

	@Override
	public void sendMessages(List<IMessage> messages) {
		for (IMessage message : messages) {
			redisTemplate.boundListOps(message.getType()).rightPush(message.getData());
		}
	}

}
