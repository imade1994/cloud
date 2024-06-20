package com.tj.cloud.core.bean;

import lombok.Getter;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

import java.util.Map;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/25 14:21
 * * @version v1.0.0
 * * @desc 动态bean
 **/
public class DynamicBean {

    /**
     * 实体Object
     */
    @Getter
    private Object target = null;

    /**
     * 属性map
     */
    private BeanMap beanMap = null;

    public DynamicBean() {
        super();
    }


    public DynamicBean(Class<?> superclass, Map<String, Class<?>> propertyMap) {
        this.target = generateBean(superclass, propertyMap);
        this.beanMap = BeanMap.create(this.target);
    }

    /**
     * bean 添加属性和值
     *
     * @param property
     * @param value
     */
    public void setValue(String property, Object value) {
        beanMap.put(property, value);
    }

    /**
     * 获取属性值
     *
     * @param property 属性名
     * @return 获取属性值
     */
    public Object getValue(String property) {
        return beanMap.get(property);
    }


    /**
     * 根据属性生成对象
     */
    private Object generateBean(Class<?> superclass, Map<String, Class<?>> propertyMap) {
        BeanGenerator generator = new BeanGenerator();
        if (null != superclass) {
            generator.setSuperclass(superclass);
        }
        BeanGenerator.addProperties(generator, propertyMap);
        return generator.create();
    }
}
