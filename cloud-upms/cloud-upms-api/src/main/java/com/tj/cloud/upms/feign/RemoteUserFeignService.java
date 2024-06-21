package com.tj.cloud.upms.feign;

import com.tj.cloud.core.constant.CommonConstant;
import com.tj.cloud.core.constant.ServiceNameConstants;
import com.tj.cloud.core.http.CloudResult;
import com.tj.cloud.core.model.authentication.dto.UserDTO;
import com.tj.cloud.core.model.authentication.pojo.UserInfoPojo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/29 15:41
 * * @version v1.0.0
 * * @desc
 **/
@FeignClient(contextId = "remoteUserFeignService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteUserFeignService {
    /**
     * 通过用户名查询用户、角色信息
     * @param user 用户查询对象
     * @param from 调用标志
     * @return R
     */
    @GetMapping("/user/info/query")
    CloudResult<UserInfoPojo> info(@SpringQueryMap UserDTO user, @RequestHeader(CommonConstant.FROM) String from);

    /**
     * 锁定用户
     * @param username 用户名
     * @param from 调用标识
     * @return
     */
    @PutMapping("/user/lock/{username}")
    CloudResult<Boolean> lockUser(@PathVariable("username") String username, @RequestHeader(CommonConstant.FROM) String from);
}
