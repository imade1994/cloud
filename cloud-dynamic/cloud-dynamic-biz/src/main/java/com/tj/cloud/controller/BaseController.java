
package com.tj.cloud.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.tj.cloud.core.http.CloudResult;
import com.tj.cloud.entity.DynamicModel;
import com.tj.cloud.service.IDynamicModelService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/20
 * @Description:
 * @version:1.0
 */
@RequestMapping
@RestController
@Api("")
public class BaseController {

	@Resource
	IDynamicModelService iDynamicModelService;

	public CloudResult<DynamicModel> queryByEntity(@RequestBody DynamicModel dynamicModel) {
		LambdaQueryWrapper<DynamicModel> queryWrapper = Wrappers.lambdaQuery();
		Map<String, String> map = JSONObject.parseObject(JSONObject.toJSONString(dynamicModel), HashMap.class);
		for (String key : map.keySet()) {
			if (ObjectUtil.isNotNull(map.get(key))) {
				if (ObjectUtil.equal(key, "id")) {
					queryWrapper.eq(DynamicModel::getId, map.get(key));
				}
				else {
					// queryWrapper.like(DynamicModel::"get".concat(key),)
				}
			}
		}
		return CloudResult.ok(null, "查询成功!");

	}

}
