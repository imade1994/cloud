package com.tj.cloud.core.utils;

import java.lang.reflect.Method;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/29 17:29
 * * @version v1.0.0
 * * @desc
 **/
public class ToStringUtils {

    @Override
    public String toString() {
        return toString(this);
    }

    /**
     * 转成字符串
     */
    public static <C> String toString(C c) {
        Class<?> cls = c.getClass();
        if (c instanceof String) {// 是字符串本身则结束
            return c + "";
        }
        try {
            Method toStringMethod = cls.getMethod("toString");// 没有toString方法 则是基础类型
            if (toStringMethod == null) {
                return c + "";
            }
        } catch (Exception e) {
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" ").append( cls.getSimpleName()).append(" [");

        for (Method method : cls.getMethods()) {
            // 获取get方法
            if (!method.getName().startsWith("get") || method.getParameters().length != 0 || "getClass".equals(method.getName())) {
                continue;
            }
            try {
                Object obj = method.invoke(c);
                String str = "";
                if (obj != null) {
                    str = obj + "";
                }
                if (!sb.toString().endsWith("[")) {
                    sb.append(", ");
                }
                sb.append(StringUtil.toFirst(method.getName().replace("get", ""), false) + "=" + str);
            } catch (Exception ignored) {
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
