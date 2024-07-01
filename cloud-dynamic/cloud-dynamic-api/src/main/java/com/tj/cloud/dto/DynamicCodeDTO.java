
package com.tj.cloud.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/18
 * @Description:
 * @version:1.0
 */
@Data
public class DynamicCodeDTO {

	/**
	 * 用来获取 生成代码文件的配置信息 dynamicCode.yaml
	 */
	private ResourceBundle generatorSetting = ResourceBundle.getBundle("dynamic/dynamicCode.yaml");

	/**
	 * 项目路径
	 */
	private String projectPath = System.getProperty("user.dir");

	/**
	 * 模块名
	 */
	private String moduleName;

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 用户名
	 */
	private String userName;

}
