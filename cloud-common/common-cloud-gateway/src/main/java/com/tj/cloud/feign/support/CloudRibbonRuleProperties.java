/*
 * Copyright (c) 2003-2021 www.hualongxunda.com/ Inc. All rights reserved.
 * 注意：本内容仅限于深圳华龙讯达信息技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.tj.cloud.feign.support;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/25
 * @Description:
 * @version:1.0
 */
@Data
@ConfigurationProperties("ribbon.rule")
public class CloudRibbonRuleProperties  implements Serializable {


    /**
     * 是否开启，默认：true
     */
    private boolean enabled = Boolean.TRUE;

    /**
     * 是否开启灰度路由，默认:false
     */
    private boolean grayEnabled;

    /**
     * 优先的ip列表，支持通配符，例如：10.20.0.8*、10.20.0.*
     */
    private List<String> priIpPattern = new ArrayList<>();
}
