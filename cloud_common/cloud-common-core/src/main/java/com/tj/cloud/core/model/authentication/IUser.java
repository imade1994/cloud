package com.tj.cloud.core.model.authentication;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/29 11:12
 * * @version v1.0.0
 * * @desc
 **/
public interface IUser {

    /**
     * 用户标识ID
     * @return String
     */
    String getId();

    /**
     * 设置用户id
     * */
    void setId(String userId);

    /**
     * 用户姓名
     *
     * @return String
     */
    String getName();


    /**
     * 设置用户姓名
     * @param fullName 用户姓名
     * */
    void setName(String fullName);

    /**
     * 用户账号
     *
     * @return String
     */
    String getAccount();


    /**
     * 用户账号
     * @param account 用户登陆账号
     * */
    void setAccount(String account);

    /**
     * 用户密码
     * @return String
     */
    String getPassword();
    /**
     * 邮件
     * @return String
     */
    String getEmail();
    /**
     * 手机。
     * @return String
     */
    Integer getMobile();

    /**
     * 用户 微信号
     * @return 用户 微信ID
     * */
    String getWechat();

    /**
     * 用户 三方接入ID
     * @return 三方ID
     * */
    String getOpenId();

    /**
     * 用户状态
     * @return 用户状态 0 禁用 1 启用
     * */
    Integer getStatus();


    /**
     * 租户ID
     * @return 获取租户ID
     * */
    String getTenantId();

}
