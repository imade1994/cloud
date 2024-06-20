package com.tj.cloud.core.http;

import com.tj.cloud.core.abs.IStatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/26 14:47
 * * @version v1.0.0
 * * @desc
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult implements IStatusCode, Serializable {

    private static final long serialVersionUID = -5106948554560742199L;
    /**
     * 本次调用是否成功
     */
    private Boolean isOk;

    /**
     * 操作提示信息
     */
    private String desc;

    /**
     * 异常堆栈信息
     */
    private String cause;

    /**
     * 状态码
     */
    private int code;

    /**
     * 来源系统
     * */
    private String system;
}
