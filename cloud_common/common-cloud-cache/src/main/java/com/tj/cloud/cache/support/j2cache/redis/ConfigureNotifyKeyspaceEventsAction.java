package com.tj.cloud.cache.support.j2cache.redis;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.connection.RedisConnection;

import java.util.Properties;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/26 13:50
 * * @version v1.0.0
 * * @desc 设置redis键值回调
 **/
public class ConfigureNotifyKeyspaceEventsAction {

    private static final String CONFIG_NOTIFY_KEYSPACE_EVENTS = "notify-keyspace-events";


    public void config(RedisConnection connection) {
        String notifyOptions = getNotifyOptions(connection);
        String customizedNotifyOptions = notifyOptions;
        if (!customizedNotifyOptions.contains("E")) {
            customizedNotifyOptions += "E";
        }
        boolean A = customizedNotifyOptions.contains("A");
        if (!(A || customizedNotifyOptions.contains("g"))) {
            customizedNotifyOptions += "g";
        }
        if (!(A || customizedNotifyOptions.contains("x"))) {
            customizedNotifyOptions += "x";
        }
        if (!notifyOptions.equals(customizedNotifyOptions)) {
            connection.setConfig(CONFIG_NOTIFY_KEYSPACE_EVENTS, customizedNotifyOptions);
        }
    }

    private String getNotifyOptions(RedisConnection connection) {
        try {
            Properties config = connection.getConfig(CONFIG_NOTIFY_KEYSPACE_EVENTS);
            if (config.isEmpty()) {
                return "";
            }
            return config.getProperty(config.stringPropertyNames().iterator().next());
        } catch (InvalidDataAccessApiUsageException e) {
            throw new IllegalStateException(
                    "Unable to configure Redis to keyspace notifications. See http://docs.spring.io/spring-session/docs/current/reference/html5/#api-redisoperationssessionrepository-sessiondestroyedevent",
                    e);
        }
    }
}
