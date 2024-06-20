package com.tj.cloud.core.utils;

import com.tj.cloud.core.enums.StatusCodeEnum;
import com.tj.cloud.core.exception.BusinessException;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/26 13:07
 * * @version v1.0.0
 * * @desc 类装载工具
 **/
public class AppUtil implements ApplicationContextAware {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AppUtil.class);

    private static ApplicationContext context;


    /**
     * @param _context 当前环境
     * */
    @Override
    public void setApplicationContext(@NonNull ApplicationContext _context)
            throws BeansException {
        context = _context;

    }

    /**
     * 获取spring容器上下文。
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * 根据beanId获取配置在系统中的对象实例。
     */
    public static Object getBean(String beanId) {
        try {
            return context.getBean(beanId);
        } catch (Exception ex) {
            LOGGER.debug("getBean:" + beanId + "," + ex.getMessage());
        }
        return null;
    }

    /**
     * 获取Spring容器的Bean
     */
    public static <T> T getBean(Class<T> beanClass) {
        try {
            return context.getBean(beanClass);
        } catch (Exception ex) {
            LOGGER.debug("getBean:" + beanClass + "," + ex.getMessage());
        }
        return null;
    }


    /**
     * 获取接口的实现类实例。
     */
    public static <T> Map<String, T> getImplInstance(Class<T> clazz) {
        return context.getBeansOfType(clazz);
    }

    public static <T> List<T> getImplInstanceArray(Class<T> clazz) {

        Map<String, T> map = context.getBeansOfType(clazz);

        return new ArrayList<>(map.values());
    }


    /**
     * 发布事件
     */
    public static void publishEvent(ApplicationEvent event) {
        if (context != null) {
            context.publishEvent(event);
        }
    }


    /**
     * 获取当前系统环境<br>
     * 目前仅支持单一环境配置。请勿配置多个参数 dev sit 之类。 环境配置的判断参考下面代码<br>
     * doGetActiveProfiles().contains(profile) || (doGetActiveProfiles().isEmpty() && doGetDefaultProfiles().contains(profile))
     */
    private static String currentProfiles = null;

    public static String getCtxEnvironment() {
        if (currentProfiles != null) {
            return currentProfiles;
        }
        Environment environment = context.getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();
        if (ObjectUtils.isNotEmpty(activeProfiles)) {
            currentProfiles = activeProfiles[0];
            return currentProfiles;
        }
        String[] defaultProfiles = environment.getDefaultProfiles();
        if (ObjectUtils.isNotEmpty(defaultProfiles)) {
            currentProfiles = defaultProfiles[0];
            return defaultProfiles[0];
        }
        throw new BusinessException("查找不到正确的环境属性配置！", StatusCodeEnum.SYSTEM_ERROR);
    }
}
