package com.tj.cloud.uid;

import com.tj.cloud.uid.exception.UidGenerateException;

/**
 * * @Author codingMan_tj * @Date 2024/4/1 10:50 * @version v1.0.0 * @desc 抽象 uid 获取接口
 **/
public interface UidGenerator {

	/**
	 * Get a unique ID
	 * @return UID
	 * @throws UidGenerateException
	 */
	long getUID() throws UidGenerateException;

	/**
	 * Parse the UID into elements which are used to generate the UID. <br>
	 * Such as timestamp & workerId & sequence...
	 * @param uid
	 * @return Parsed info
	 */
	String parseUID(long uid);

}
