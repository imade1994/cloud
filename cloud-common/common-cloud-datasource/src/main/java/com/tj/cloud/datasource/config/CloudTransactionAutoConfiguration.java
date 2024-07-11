package com.tj.cloud.datasource.config;

import com.tj.cloud.datasource.transaction.CloudDataSourceTransactionManager;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.AbstractTransactionManagementConfiguration;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.config.TransactionManagementConfigUtils;
import org.springframework.transaction.interceptor.*;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 10:22 * @version v1.0.0 * @desc
 **/
@Configuration
public class CloudTransactionAutoConfiguration extends AbstractTransactionManagementConfiguration {

	private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.tj.cloud..manager.*.*(..))||execution(* com.tj.cloud.*.manager.*.*(..))";

	private static final String MONGODB_TX_CLASS = "com.tj.cloud.db.core.config.mongodb.HlMongodbDataSourceTransactionManager";

	private static final String RW_TX_INTERCEPTOR_CLASS = "com.tj.cloud.db.core.readwrite.HlRwTransactionInterceptor";

	@Bean
	public PlatformTransactionManager transactionManager() {
		// Create different things manager
		PlatformTransactionManager platformTransactionManager = new CloudDataSourceTransactionManager();
		/*
		 * if (ClassUtilsExtend.isPresent(MONGODB_TX_CLASS,
		 * this.getClass().getClassLoader())) { platformTransactionManager = new
		 * HlMongodbDataSourceTransactionManager(); } else { platformTransactionManager =
		 * new HlCloudDataSourceTransactionManager(); }
		 */
		txManager = platformTransactionManager;
		return platformTransactionManager;
	}

	@Bean(name = "hlTransactionInterceptor")
	public TransactionInterceptor abTransactionInterceptor() {
		DefaultTransactionAttribute requiredDTA = new DefaultTransactionAttribute();
		requiredDTA.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		DefaultTransactionAttribute requiredReadonlyDTA = new DefaultTransactionAttribute();
		requiredReadonlyDTA.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		requiredReadonlyDTA.setReadOnly(true);

		NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
		source.addTransactionalMethod("*", requiredDTA);
		source.addTransactionalMethod("get*", requiredReadonlyDTA);
		source.addTransactionalMethod("query*", requiredReadonlyDTA);
		source.addTransactionalMethod("find*", requiredReadonlyDTA);
		source.addTransactionalMethod("is*", requiredReadonlyDTA);
		source.addTransactionalMethod("select*", requiredReadonlyDTA);

		TransactionInterceptor transactionInterceptor = createTransactionInterceptor();
		transactionInterceptor.setTransactionAttributeSources(annotationTransactionAttributeSource(), source);
		transactionInterceptor.setTransactionManager(transactionManager());
		return transactionInterceptor;
	}

	private TransactionInterceptor createTransactionInterceptor() {
		TransactionInterceptor transactionInterceptor = null;
		if (ClassUtils.isPresent(RW_TX_INTERCEPTOR_CLASS, getClass().getClassLoader())) {
			try {
				Class<?> clazz = Class.forName(RW_TX_INTERCEPTOR_CLASS);
				transactionInterceptor = (TransactionInterceptor) clazz.getConstructor().newInstance();
			}
			catch (ReflectiveOperationException e) {
				ReflectionUtils.rethrowRuntimeException(e);
			}
		}
		else {
			transactionInterceptor = new TransactionInterceptor();
		}
		return transactionInterceptor;
	}

	// @Bean(name = "txAdviceAdvisor")
	public Advisor txAdviceAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
		return new DefaultPointcutAdvisor(pointcut, abTransactionInterceptor());
	}

	@Bean(name = TransactionManagementConfigUtils.TRANSACTION_ADVISOR_BEAN_NAME)
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public BeanFactoryTransactionAttributeSourceAdvisor transactionAdvisor() {
		BeanFactoryTransactionAttributeSourceAdvisor advisor = new BeanFactoryTransactionAttributeSourceAdvisor();
		advisor.setTransactionAttributeSource(annotationTransactionAttributeSource());
		advisor.setAdvice(abTransactionInterceptor());
		if (this.enableTx != null) {
			advisor.setOrder(this.enableTx.<Integer>getNumber("order"));
		}
		return advisor;
	}

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public TransactionAttributeSource annotationTransactionAttributeSource() {
		return new AnnotationTransactionAttributeSource();
	}

}
