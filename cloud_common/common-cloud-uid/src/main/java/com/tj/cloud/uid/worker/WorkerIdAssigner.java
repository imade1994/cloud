package com.tj.cloud.uid.worker;

/**
 * * @Author codingMan_tj
 * * @Date 2024/4/1 13:30
 * * @version v1.0.0
 * * @desc 抽象wokerId 分配接口
 **/
public interface WorkerIdAssigner {

    /**
     * Assign worker id for {@link com.baidu.fsg.uid.impl.DefaultUidGenerator}
     *
     * @return assigned worker id
     */
    long assignWorkerId();
}
