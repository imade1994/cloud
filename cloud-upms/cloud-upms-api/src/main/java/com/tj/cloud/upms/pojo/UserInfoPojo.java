package com.tj.cloud.upms.pojo;

import com.tj.cloud.upms.entity.SysPost;
import com.tj.cloud.upms.entity.SysRole;
import com.tj.cloud.upms.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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
	private String[] roles;

	/**
	 * 角色集合
	 */
	private List<SysRole> roleList;

	/**
	 * 岗位集合
	 */
	private String[] posts;

	/**
	 * 岗位集合
	 */
	private List<SysPost> postList;

}
