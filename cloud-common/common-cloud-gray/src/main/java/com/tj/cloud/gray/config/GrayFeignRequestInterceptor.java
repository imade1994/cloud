package com.tj.cloud.gray.config;

import cn.hutool.core.util.StrUtil;
import com.tj.cloud.core.utils.WebUtils;
import com.tj.cloud.feign.constant.GatewayConstant;
import com.tj.cloud.gray.support.VersionHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/16
 * @Description:
 * @version:1.0
 */
public class GrayFeignRequestInterceptor implements RequestInterceptor {


    private static final Logger log = LoggerFactory.getLogger(GrayFeignRequestInterceptor.class);

    /**
     * Called for every request. Add data using methods on the supplied
     * {@link RequestTemplate}.
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        String reqVersion = WebUtils.getRequest().isPresent() ? WebUtils.getOriRequest().getHeader(GatewayConstant.SERVICE_VERSION)
                : VersionHolder.getVersion();

        if (StrUtil.isNotBlank(reqVersion)) {
            log.debug("feign gray add header version : {}", reqVersion);
            template.header(GatewayConstant.SERVICE_VERSION, reqVersion);
        }
    }
}
