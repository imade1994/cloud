package com.tj.cloud.security.authentication;

import cn.hutool.core.util.StrUtil;
import com.tj.cloud.core.constant.CommonConstant;
import com.tj.cloud.security.annotations.Inner;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 16:27 * @version v1.0.0 * @desc
 **/
@Aspect
@RequiredArgsConstructor
public class CloudSecurityInnerAspect implements Ordered {

	private static final Logger log = LoggerFactory.getLogger(CloudSecurityInnerAspect.class);

	private final HttpServletRequest request;

	@SneakyThrows
	@Before("@within(inner) || @annotation(inner)")
	public void around(JoinPoint point, Inner inner) {
		// 实际注入的inner实体由表达式后一个注解决定，即是方法上的@Inner注解实体，若方法上无@Inner注解，则获取类上的
		if (inner == null) {
			Class<?> clazz = point.getTarget().getClass();
			inner = AnnotationUtils.findAnnotation(clazz, Inner.class);
		}
		String header = request.getHeader(CommonConstant.FROM);
		if (inner.value() && !StrUtil.equals(CommonConstant.FROM_IN, header)) {
			log.warn("访问接口 {} 没有权限", point.getSignature().getName());
			throw new AccessDeniedException("Access is denied");
		}
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 1;
	}

}
