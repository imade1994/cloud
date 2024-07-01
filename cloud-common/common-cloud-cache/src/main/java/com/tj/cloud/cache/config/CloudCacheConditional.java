package com.tj.cloud.cache.config;

import com.tj.cloud.cache.enums.CloudCacheType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.util.ObjectUtils;

/**
 * * @Author codingMan_tj * @Date 2024/3/25 15:41 * @version v1.0.0 * @desc 缓存注入判定类
 **/
public class CloudCacheConditional extends SpringBootCondition {

	private static final Logger log = LoggerFactory.getLogger(CloudCacheConditional.class);

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String sourceClass = "";
		if (metadata instanceof ClassMetadata) {
			sourceClass = ((ClassMetadata) metadata).getClassName();
		}
		String value = context.getEnvironment().getProperty("cloud.cache.type");
		if (ObjectUtils.isEmpty(value)) {
			return CloudMemoryCacheConfiguration.class.getName().equals(sourceClass) ? ConditionOutcome.match()
					: ConditionOutcome.noMatch(value + " cache type");
		}
		value = value.replace("-", "_").toUpperCase();
		CloudCacheType cacheType = CloudCacheType.valueOf(value);
		return cacheType.getConfigurationClass().getName().equals(sourceClass) ? ConditionOutcome.match()
				: ConditionOutcome.noMatch(value + " cache type");
	}

}
