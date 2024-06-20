package com.tj.cloud.core.model.authentication.vo;

import com.tj.cloud.core.model.authentication.IUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/29 11:50
 * * @version v1.0.0
 * * @desc 登陆用户实体
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements IUser, Serializable {


    private String id;


    private String name;


    private String account;


    private String password;


    private String email;


    private String wechat;


    private String openId;


    private Integer mobile;


    private Integer status;


    private String tenantId;

    private String deptId;

    private String orgId;

    private LocalDateTime expireDate;


    private Integer autoRenewal;



}
