package com.tj.cloud.cache.support.redis;

import org.nustaq.serialization.FSTConfiguration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * * @Author codingMan_tj * @Date 2024/3/26 13:39 * @version v1.0.0 * @desc 自定义redis 序列化
 **/
public class FastRedisSerializer implements RedisSerializer<Object> {

	private final FSTConfiguration fstConfiguration;

	private final byte[] EMPTY_BYTES = new byte[0];

	public FastRedisSerializer() {
		fstConfiguration = FSTConfiguration.getDefaultConfiguration();
		fstConfiguration.setClassLoader(FastRedisSerializer.class.getClassLoader());
	}

	@Override
	public byte[] serialize(Object o) throws SerializationException {
		if (o == null) {
			return EMPTY_BYTES;
		}
		return fstConfiguration.asByteArray(o);
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		return fstConfiguration.asObject(bytes);
	}

}
