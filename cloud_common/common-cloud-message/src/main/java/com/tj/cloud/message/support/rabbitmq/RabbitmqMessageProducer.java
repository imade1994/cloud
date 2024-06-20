package com.tj.cloud.message.support.rabbitmq;

import com.tj.cloud.core.abs.IMessage;
import com.tj.cloud.core.abs.IMessageProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

/**
 * * @Author codingMan_tj
 * * @Date 2024/4/15 17:44
 * * @version v1.0.0
 * * @desc
 **/
public class RabbitmqMessageProducer implements IMessageProducer {

    private final RabbitTemplate rabbitTemplate;


    public RabbitmqMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMessage(IMessage message) {
        String[] strs = message.getType().split(":");
        rabbitTemplate.convertAndSend(strs[0],strs[1],message.getData());
    }

    @Override
    public void sendMessages(List<IMessage> messages) {
        for(IMessage message:messages){
            String[] strs = message.getType().split(":");
            rabbitTemplate.convertAndSend(strs[0],strs[1],message.getData());
        }

    }
}
