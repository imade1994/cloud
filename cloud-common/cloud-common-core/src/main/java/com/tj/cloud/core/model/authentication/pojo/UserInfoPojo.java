package com.tj.cloud.core.model.authentication.pojo;

import com.tj.cloud.core.model.authentication.vo.User;
import lombok.Data;

import java.io.Serializable;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 14:32 * @version v1.0.0 * @desc
 **/
@Data
public class UserInfoPojo implements Serializable {

	/**
	 * 用户基本信息
	 */
	private User user;

	/**
	 * 权限标识集合
	 */
	private String[] permissions;

	/**
	 * 角色集合
	 */
	private Long[] roles;

}
