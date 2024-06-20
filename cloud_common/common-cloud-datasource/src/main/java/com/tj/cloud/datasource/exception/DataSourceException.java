package com.tj.cloud.datasource.exception;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/28 11:31
 * * @version v1.0.0
 * * @desc
 **/
public class DataSourceException extends RuntimeException{
    private static final long serialVersionUID = -2166820983685470213L;

    public DataSourceException(String msg) {
        super(msg);
    }

    public DataSourceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
