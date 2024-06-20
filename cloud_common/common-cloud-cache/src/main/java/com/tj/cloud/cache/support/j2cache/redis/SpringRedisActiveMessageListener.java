package com.tj.cloud.cache.support.j2cache.redis;

import net.oschina.j2cache.cluster.ClusterPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/26 13:50
 * * @version v1.0.0
 * * @desc 监听二缓key失效，主动清除本地缓存
 **/
public class SpringRedisActiveMessageListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(SpringRedisActiveMessageListener.class);

    private ClusterPolicy clusterPolicy;

    private String namespace;

    SpringRedisActiveMessageListener(ClusterPolicy clusterPolicy, String namespace) {
        this.clusterPolicy = clusterPolicy;
        this.namespace = namespace;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = message.toString();
        if (key == null) {
            return;
        }
        if (key.startsWith(namespace + ":")) {
            String[] k = key.replaceFirst(namespace + ":", "").split(":", 2);
            if (k.length != 2) {
                return;
            }
            clusterPolicy.evict(k[0], k[1]);
        }

    }
}
