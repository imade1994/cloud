package com.tj.cloud.core.abs;

import java.util.List;

/**
 * * @Author codingMan_tj * @Date 2024/4/10 14:50 * @version v1.0.0 * @desc
 **/
public interface IMessageProducer {

	/**
	 * 发送单挑消息
	 * @param message 发送的消息
	 */
	void sendMessage(IMessage message);

	/**
	 * 批量发送消息
	 * @param messages 批量消息
	 */
	void sendMessages(List<IMessage> messages);

}
