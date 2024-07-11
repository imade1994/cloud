
package com.tj.cloud.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tj.cloud.upms.entity.SysPost;

import java.util.List;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
public interface ISysPostService extends IService<SysPost> {

	/**
	 * 通过用户ID，查询岗位信息
	 * @param userId 用户id
	 * @return 岗位信息
	 */
	List<SysPost> listPostsByUserId(String userId);

}
