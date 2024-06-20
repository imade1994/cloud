package com.tj.cloud.datasource.event;

import org.springframework.context.ApplicationEvent;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/28 11:37
 * * @version v1.0.0
 * * @desc
 **/
public class DataSourceCloseEvent extends ApplicationEvent {

    public DataSourceCloseEvent(Object source) {
        super(source);
    }
}
