
package com.tj.cloud.security.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/21
 * @Description:
 * @version:1.0
 */
@Data
public class TokenVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private Long userId;

	private String clientId;

	private String username;

	private String accessToken;

	private String issuedAt;

	private String expiresAt;

}
