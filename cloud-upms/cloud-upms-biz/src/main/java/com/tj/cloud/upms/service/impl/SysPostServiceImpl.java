
package com.tj.cloud.upms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tj.cloud.upms.entity.SysPost;
import com.tj.cloud.upms.mapper.SysPostMapper;
import com.tj.cloud.upms.service.ISysPostService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {

	@Override
	public List<SysPost> listPostsByUserId(String userId) {
		return this.baseMapper.listPostsByUserId(userId);
	}

}
