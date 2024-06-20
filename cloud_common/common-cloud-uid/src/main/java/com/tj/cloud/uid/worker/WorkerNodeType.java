package com.tj.cloud.uid.worker;

import com.tj.cloud.uid.utils.ValuedEnum;

/**
 * * @Author codingMan_tj
 * * @Date 2024/4/1 13:30
 * * @version v1.0.0
 * * @desc wokeNode 枚举类 容器 和实体机
 **/
public enum WorkerNodeType implements ValuedEnum<Integer> {
    CONTAINER(1), ACTUAL(2);

    /**
     * Lock type
     */
    private final Integer type;

    /**
     * Constructor with field of type
     */
    private WorkerNodeType(Integer type) {
        this.type = type;
    }

    @Override
    public Integer value() {
        return type;
    }
}
