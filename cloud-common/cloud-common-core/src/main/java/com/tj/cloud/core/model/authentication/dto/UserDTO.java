package com.tj.cloud.core.model.authentication.dto;

import com.tj.cloud.core.model.authentication.vo.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 15:32 * @version v1.0.0 * @desc
 **/
@Data
public class UserDTO extends User implements Serializable {

	private List<String> roles;

	private List<String> orgIds;

	private List<String> deptIds;

	private String newPassword;

}
