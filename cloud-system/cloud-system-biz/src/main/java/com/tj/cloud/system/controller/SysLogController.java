/*
 * Copyright (c) 2003-2021 www.hualongxunda.com/ Inc. All rights reserved.
 * 注意：本内容仅限于深圳华龙讯达信息技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.tj.cloud.system.controller;

import com.tj.cloud.core.http.CloudResult;
import com.tj.cloud.security.annotations.Inner;
import com.tj.cloud.system.entity.SysLog;
import com.tj.cloud.system.service.ISysLogService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@RestController
@RequestMapping("/log")
@Api("日志管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysLogController {

    @Resource
    ISysLogService sysLogService;


    /**
     * 插入日志
     * @param sysLog 日志实体
     * @return success/false
     */
    @Inner
    @PostMapping("/save")
    public CloudResult<Boolean> save(@Valid @RequestBody SysLog sysLog) {
        return CloudResult.ok(sysLogService.save(sysLog));
    }

}
