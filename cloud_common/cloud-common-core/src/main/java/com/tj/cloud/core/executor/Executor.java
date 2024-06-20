package com.tj.cloud.core.executor;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/25 14:02
 * * @version v1.0.0
 * * @desc 校验器
 **/
public interface Executor<T> extends Comparable<Executor<T>> {

    /**
     * 执行器的key
     * @return 执行器的key
     */
    String getKey();

    /**
     * 执行器的名称
     * @return 执行器的名称
     */
    String getName();

    /**
     * 返回这个执行器的类型key
     * @return 返回这个执行器的类型key
     */
    String type();

    /**
     * 返回校验器的别名
     * 多个以,分隔，eg：a,b,c,...
     * @return 返回校验器的别名
     */
    String getCheckerKey();

    /**
     * 序号
     * 用于某些执行器有先后顺序的
     * @return 返回校验器顺序
     */
    int getSn();

    /**
     * 运行这个执行器
     * 运行前要通过校验
     */
    void execute(T param);
}
