package com.tj.cloud.cache.support.j2cache.redis;

import net.oschina.j2cache.Command;
import net.oschina.j2cache.cluster.ClusterPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * * @Author codingMan_tj * @Date 2024/3/26 13:53 * @version v1.0.0 * @desc
 **/
public class SpringRedisMessageListener implements MessageListener {

	private final static Logger logger = LoggerFactory.getLogger(SpringRedisMessageListener.class);

	/**
	 * 命令源标识，随机生成，每个节点都有唯一标识
	 */
	private final int LOCAL_COMMAND_ID = Command.genRandomSrc();

	private ClusterPolicy clusterPolicy;

	private String channel;

	private RedisTemplate redisTemplate;

	SpringRedisMessageListener(RedisTemplate redisTemplate, ClusterPolicy clusterPolicy, String channel) {
		this.clusterPolicy = clusterPolicy;
		this.channel = channel;
		this.redisTemplate = redisTemplate;
	}

	private boolean isLocalCommand(Command cmd) {
		return cmd.getSrc() == LOCAL_COMMAND_ID;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		byte[] messageChannel = message.getChannel();
		byte[] messageBody = message.getBody();
		if (messageChannel == null || messageBody == null) {
			return;
		}
		try {
			Command cmd = Command.parse((String) redisTemplate.getValueSerializer().deserialize(messageBody));
			if (cmd == null || isLocalCommand(cmd)) {
				return;
			}

			switch (cmd.getOperator()) {
				case Command.OPT_JOIN:
					logger.info("Node-" + cmd.getSrc() + " joined to " + this.channel);
					break;
				case Command.OPT_EVICT_KEY:
					clusterPolicy.evict(cmd.getRegion(), cmd.getKeys());
					logger.debug("Received cache evict message, region=" + cmd.getRegion() + ",key="
							+ String.join(",", cmd.getKeys()));
					break;
				case Command.OPT_CLEAR_KEY:
					clusterPolicy.clear(cmd.getRegion());
					logger.debug("Received cache clear message, region=" + cmd.getRegion());
					break;
				case Command.OPT_QUIT:
					logger.info("Node-" + cmd.getSrc() + " quit to " + this.channel);
					break;
				default:
					logger.warn("Unknown message type = " + cmd.getOperator());
			}
		}
		catch (Exception e) {
			logger.error("Failed to handle received msg", e);
		}
	}

}
