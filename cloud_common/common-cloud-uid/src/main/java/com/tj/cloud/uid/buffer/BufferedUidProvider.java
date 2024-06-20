package com.tj.cloud.uid.buffer;

import java.util.List;

/**
 * * @Author codingMan_tj
 * * @Date 2024/4/1 14:08
 * * @version v1.0.0
 * * @desc uid 提供 者
 **/
public interface BufferedUidProvider {

    /**
     * Provides UID in one second
     *
     * @param momentInSecond
     * @return
     */
    List<Long> provide(long momentInSecond);
}
