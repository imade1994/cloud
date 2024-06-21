package com.tj.cloud.uid.buffer;

/**
 * * @Author codingMan_tj * @Date 2024/4/1 14:10 * @version v1.0.0 * @desc 当获取 uid
 * 的游标指向环的尾端时 所有获取请求会被拒绝
 **/
@FunctionalInterface
public interface RejectedTakeBufferHandler {

	/**
	 * Reject take buffer request
	 * @param ringBuffer
	 */
	void rejectTakeBuffer(RingBuffer ringBuffer);

}
