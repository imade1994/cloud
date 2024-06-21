package com.tj.cloud.uid.buffer;

/**
 * * @Author codingMan_tj * @Date 2024/4/1 14:09 * @version v1.0.0 * @desc buffer
 * 环填充满了以后的拒绝策略
 **/
@FunctionalInterface
public interface RejectedPutBufferHandler {

	/**
	 * Reject put buffer request
	 * @param ringBuffer
	 * @param uid
	 */
	void rejectPutBuffer(RingBuffer ringBuffer, long uid);

}
