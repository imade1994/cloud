package com.tj.cloud.system.controller;

import com.tj.cloud.core.annotations.CatchError;
import com.tj.cloud.core.http.CloudResult;
import com.tj.cloud.system.entity.SysRoute;
import com.tj.cloud.system.service.ISysRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/16
 * @Description:
 * @version:1.0
 */
@RestController
@RequestMapping("/route")
public class SysRouteController {


    private static final Logger logger = LoggerFactory.getLogger(SysRouteController.class);



    @Resource
    ISysRouteService sysRouteService;





    @PostMapping("/add")
    @CatchError
    public CloudResult<String> addRoute(@RequestBody SysRoute sysRoute){
        return sysRouteService.save(sysRoute)?CloudResult.ok("sys"):CloudResult.failed("sys");
    }
}
