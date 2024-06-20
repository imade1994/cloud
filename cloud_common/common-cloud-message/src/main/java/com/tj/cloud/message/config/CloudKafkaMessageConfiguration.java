package com.tj.cloud.message.config;

import com.tj.cloud.core.abs.IMessageProducer;
import com.tj.cloud.message.properties.KafkaProperties;
import com.tj.cloud.message.support.kafka.KafkaMessageProducer;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * * @Author codingMan_tj
 * * @Date 2024/4/9 16:17
 * * @version v1.0.0
 * * @desc
 **/
@EnableConfigurationProperties(KafkaProperties.class)
public class CloudKafkaMessageConfiguration {


    private static final String SECURITY_LOGIN_CONFIG = "java.security.auth.login.config";
    private static final String SECURITY_PROTOCOL = "security.protocol";
    private static final String SECURITY_SASL_MECHANISM = "sasl.mechanism";


    @Resource
    KafkaProperties kafkaProperties;

    @Bean("kafkaProducerMap")
    public Map<String,KafkaProducer<String,Object>> kafkaProducerMap(){
        Map<String,KafkaProducer<String,Object>> producerMap = new HashMap<>();
        if(ObjectUtils.isNotEmpty(kafkaProperties.getProduucerList())){
            for (KafkaProperties.Producer producer: kafkaProperties.getProduucerList()){
                Properties properties = new Properties();
                if(kafkaProperties.isAuth()){
                    properties.put(SECURITY_LOGIN_CONFIG,kafkaProperties.getLoginConfig());
                    properties.put(SECURITY_PROTOCOL,kafkaProperties.getSecurityProtocol());
                    properties.put(SECURITY_SASL_MECHANISM,kafkaProperties.getSaslMechanism());
                }

                // 服务器地址
                properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getServers());
                // ACK机制，默认为1 (0,1,-1)
                properties.setProperty(ProducerConfig.ACKS_CONFIG, producer.getAckMode());
                // Socket发送消息缓冲区大小，默认为128K，设置为-1代表操作系统的默认值
                properties.setProperty(ProducerConfig.SEND_BUFFER_CONFIG,producer.getSendBuffer());
                // 生产者客户端发送消息的最大值，默认1M
                properties.setProperty(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, producer.getRequestSize());
                // 发送消息异常时重试次数，默认为0
                properties.setProperty(ProducerConfig.RETRIES_CONFIG, producer.getRetry());
                // 重试间隔时间，默认100
                properties.setProperty(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, producer.getRetryBackoff());
                // 生产消息自定义分区策略类
                properties.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, producer.getPartitionConfig());
                // 开启幂等 ,默认true
                properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, producer.getIdempotence());
                producerMap.put(producer.getTopicName(),new KafkaProducer<>(properties));
            }
        }
        return producerMap;
    }


    @Bean
    IMessageProducer iMessageProducer(@Qualifier("kafkaProducerMap")Map<String,KafkaProducer<String,Object>> map){
        return new KafkaMessageProducer(map);
    }

}
