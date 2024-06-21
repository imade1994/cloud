package com.tj.cloud.core.abs;

import java.io.Serializable;

/**
 * * @Author codingMan_tj * @Date 2024/4/10 14:43 * @version v1.0.0 * @desc
 **/
public interface IMessage<T extends Serializable> extends Serializable {

	/**
	 * 具体消费者的标识
	 * @return 消费者标识
	 */
	String getType();

	/**
	 * 消费者的数据对象
	 * @return 消费数据
	 */
	T getData();

}
