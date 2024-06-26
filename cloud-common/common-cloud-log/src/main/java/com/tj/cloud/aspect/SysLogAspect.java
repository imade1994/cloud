
package com.tj.cloud.aspect;

import cn.hutool.core.util.StrUtil;

import com.tj.cloud.core.utils.SpringContextHolder;
import com.tj.cloud.enumeration.LogTypeEnum;
import com.tj.cloud.event.SysLogEvent;
import com.tj.cloud.system.entity.SysLog;
import com.tj.cloud.util.SysLogUtils;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@Aspect
public class SysLogAspect {

	private static final Logger log = LoggerFactory.getLogger(SysLogAspect.class);

	@Around("@annotation(sysLog)")
	@SneakyThrows
	public Object around(ProceedingJoinPoint point, com.tj.cloud.annotation.SysLog sysLog) {
		String strClassName = point.getTarget().getClass().getName();
		String strMethodName = point.getSignature().getName();
		log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

		String value = sysLog.value();
		String expression = sysLog.expression();
		// 当前表达式存在 SPEL，会覆盖 value 的值
		if (StrUtil.isNotBlank(expression)) {
			// 解析SPEL
			MethodSignature signature = (MethodSignature) point.getSignature();
			EvaluationContext context = SysLogUtils.getContext(point.getArgs(), signature.getMethod());
			try {
				value = SysLogUtils.getValue(context, expression, String.class);
			}
			catch (Exception e) {
				// SPEL 表达式异常，获取 value 的值
				log.error("@SysLog 解析SPEL {} 异常", expression);
			}
		}

		SysLog logVo = SysLogUtils.getSysLog();
		logVo.setTitle(value);

		// 发送异步日志事件
		Long startTime = System.currentTimeMillis();
		Object obj;

		try {
			obj = point.proceed();
		}
		catch (Exception e) {
			logVo.setType(LogTypeEnum.ERROR.getType());
			logVo.setException(e.getMessage());
			throw e;
		}
		finally {
			Long endTime = System.currentTimeMillis();
			logVo.setTime(endTime - startTime);
			SpringContextHolder.publishEvent(new SysLogEvent(logVo));
		}

		return obj;
	}

}
