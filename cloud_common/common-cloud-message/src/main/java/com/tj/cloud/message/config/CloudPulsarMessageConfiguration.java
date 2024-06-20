package com.tj.cloud.message.config;

import com.tj.cloud.message.properties.PulsarProperties;
import com.tj.cloud.message.support.pulsar.PulsarMessageProducer;
import org.apache.pulsar.client.api.AuthenticationFactory;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * * @Author codingMan_tj
 * * @Date 2024/4/9 16:17
 * * @version v1.0.0
 * * @desc
 **/
@EnableConfigurationProperties(PulsarProperties.class)
public class CloudPulsarMessageConfiguration {

    @Resource
    PulsarProperties pulsarProperties;




    @Bean
    public PulsarClient pulsarClient() throws PulsarClientException {
        return PulsarClient.builder()
                .serviceUrl(pulsarProperties.getServiceUrl())
                .authentication(AuthenticationFactory.token(pulsarProperties.getToken()))
                .tlsCertificateFilePath(pulsarProperties.getTlsFile())
                .ioThreads(pulsarProperties.getIoThreads())
                .listenerThreads(pulsarProperties.getListenerThreads())
                .build();
    }



    @Bean("pulsarProducerMap")
    public Map<String, Producer<byte[]>> pulsarClientMap(@Qualifier PulsarClient pulsarClient) throws PulsarClientException {
        Map<String,Producer<byte[]>> producerMap = new HashMap<>();
        for (PulsarProperties.Producer producer: pulsarProperties.getProducerList()){
            producerMap.put(producer.getTopicName(),pulsarClient.newProducer()
                            .topic(producer.getTopicName())
                            .producerName(producer.getProducerName())
                    .create());
        }
        return producerMap;
    }


    @Bean
    PulsarMessageProducer pulsarMessageProducer(@Qualifier("pulsarProducerMap") Map<String, Producer<byte[]>> map){
        return new PulsarMessageProducer(map);
    }
}
