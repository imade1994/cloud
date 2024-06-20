package com.tj.cloud.cache.support.j2cache.redis;

import com.alibaba.fastjson2.JSON;
import com.tj.cloud.cache.support.j2cache.constant.J2CacheConstant;
import net.oschina.j2cache.CacheProviderHolder;
import net.oschina.j2cache.Command;
import net.oschina.j2cache.cluster.ClusterPolicy;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/26 13:57
 * * @version v1.0.0
 * * @desc
 **/
public class SpringRedisPubSubPolicy implements ClusterPolicy {

    /**
     * 命令源标识，随机生成，每个节点都有唯一标识
     */
    private final int LOCAL_COMMAND_ID = Command.genRandomSrc();

    private RedisTemplate<String, Serializable> redisTemplate;

    private CacheProviderHolder holder;

    /**
     * 是否是主动模式
     */
    private static boolean isActive = false;

    private String channel = "j2cache_channel";

    private boolean l2CacheOpen;

    @Override
    public boolean isLocalCommand(Command cmd) {
        return cmd.getSrc() == LOCAL_COMMAND_ID;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void connect(Properties props, CacheProviderHolder holder) {
        this.holder = holder;
        this.l2CacheOpen = BooleanUtils.toBoolean(props.getProperty(J2CacheConstant.CACHE_OPEN));
        if (!this.l2CacheOpen) {
            return;
        }
        this.redisTemplate = (RedisTemplate) Objects.requireNonNull(props.get(J2CacheConstant.REDIS_TEMPLATE), "Not found RedisTemplate bean");
        String channelName = props.getProperty("channel");
        if (channelName != null && !channelName.isEmpty()) {
            this.channel = channelName;
        }
        ApplicationContext applicationContext = (ApplicationContext) props.get(J2CacheConstant.SPRING_APPLICATION_CONTEXT);
        RedisMessageListenerContainer redisMessageListenerContainer = applicationContext.getBean(J2CacheConstant.HL_REDIS_MESSAGE_CONTAINER_BEAN_ID, RedisMessageListenerContainer.class);
        String database = "0";
        if (redisMessageListenerContainer.getConnectionFactory() instanceof LettuceConnectionFactory) {
            database = String.valueOf(((LettuceConnectionFactory) redisMessageListenerContainer.getConnectionFactory()).getDatabase());
        } else if (redisMessageListenerContainer.getConnectionFactory() instanceof JedisConnectionFactory) {
            database = String.valueOf(((JedisConnectionFactory) redisMessageListenerContainer.getConnectionFactory()).getDatabase());
        }
        String namespace = StringUtils.defaultIfEmpty(props.getProperty("namespace"), J2CacheConstant.DEFAULT_REGION);
        String expired = "__keyevent@" + database + "__:expired";
        String del = "__keyevent@" + database + "__:del";
        List<PatternTopic> topics = new ArrayList<>();
        topics.add(new PatternTopic(expired));
        topics.add(new PatternTopic(del));

        if ("active".equals(props.getProperty(J2CacheConstant.CACHE_CLEAN_MODE))) {
            isActive = true;
            //设置键值回调 需要redis支持键值回调
            ConfigureNotifyKeyspaceEventsAction action = new ConfigureNotifyKeyspaceEventsAction();
            action.config(redisMessageListenerContainer.getConnectionFactory().getConnection());
            redisMessageListenerContainer.addMessageListener(new SpringRedisActiveMessageListener(this, namespace), topics);
        } else if ("blend".equals(props.getProperty(J2CacheConstant.CACHE_CLEAN_MODE))) {
            //设置键值回调 需要redis支持键值回调
            ConfigureNotifyKeyspaceEventsAction action = new ConfigureNotifyKeyspaceEventsAction();
            action.config(redisMessageListenerContainer.getConnectionFactory().getConnection());
            redisMessageListenerContainer.addMessageListener(new SpringRedisActiveMessageListener(this, namespace), topics);
            redisMessageListenerContainer.addMessageListener(new SpringRedisMessageListener(redisTemplate, this, this.channel), new PatternTopic(this.channel));
        } else {
            redisMessageListenerContainer.addMessageListener(new SpringRedisMessageListener(redisTemplate, this, this.channel), new PatternTopic(this.channel));
        }

    }

    /**
     * 删除本地某个缓存条目
     */
    @Override
    public void evict(String region, String... keys) {
        holder.getLevel1Cache(region).evict(keys);
    }

    /**
     * 清除本地整个缓存区域
     */
    @Override
    public void clear(String region) {
        holder.getLevel1Cache(region).clear();
    }

    @Override
    public void publish(Command cmd) {
        if (!isActive && l2CacheOpen) {
            cmd.setSrc(LOCAL_COMMAND_ID);
            redisTemplate.convertAndSend(this.channel, JSON.toJSONString(cmd));
        }
    }

    @Override
    public void disconnect() {
        if (!isActive && l2CacheOpen) {
            Command cmd = new Command();
            cmd.setSrc(LOCAL_COMMAND_ID);
            cmd.setOperator(Command.OPT_QUIT);
            redisTemplate.convertAndSend(this.channel, JSON.toJSONString(cmd));
        }
    }
}
