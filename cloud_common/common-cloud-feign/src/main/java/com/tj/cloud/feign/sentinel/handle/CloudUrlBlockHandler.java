package com.tj.cloud.feign.sentinel.handle;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tj.cloud.core.http.CloudResult;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/26 15:27
 * * @version v1.0.0
 * * @desc
 * * sentinel统一降级限流策略
 * * <p>
 * * {@link com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.DefaultBlockExceptionHandler}
 **/

@RequiredArgsConstructor
public class CloudUrlBlockHandler implements BlockExceptionHandler {


    private final static Logger log = LoggerFactory.getLogger(BlockExceptionHandler.class);
    private final ObjectMapper objectMapper;


    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        log.error("sentinel 降级 资源名称{}", e.getRule().getResource(), e);

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON.getType());
        httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        httpServletResponse.getWriter().print(objectMapper.writeValueAsString(CloudResult.failed(e.getMessage())));
    }
}
