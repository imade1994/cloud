package com.tj.cloud.datasource.readwrite;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.lang.NonNull;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.lang.reflect.Method;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/28 11:13
 * * @version v1.0.0
 * * @desc
 **/
public class RwTransactionInterceptor extends TransactionInterceptor {

    public RwTransactionInterceptor() {
    }

    @Override
    public Object invoke(@NonNull MethodInvocation invocation) throws Throwable {
        return super.invoke(invocation);
    }

    @Override
    protected Object invokeWithinTransaction(@NonNull Method method, Class<?> targetClass,@NonNull InvocationCallback invocation) throws Throwable {
        TransactionAttributeSource tas = getTransactionAttributeSource();
        final TransactionAttribute txAttr = (tas != null ? tas.getTransactionAttribute(method, targetClass) : null);
        ReadWriteSplitHolder.bindReadOnly(txAttr != null && txAttr.isReadOnly());
        try {
            return super.invokeWithinTransaction(method, targetClass, invocation);
        } finally {
            ReadWriteSplitHolder.restoreReadOnly();
        }
    }
}
