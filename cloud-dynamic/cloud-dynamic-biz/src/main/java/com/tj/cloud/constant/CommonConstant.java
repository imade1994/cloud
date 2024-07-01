
package com.tj.cloud.constant;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/18
 * @Description:
 * @version:1.0
 */
public interface CommonConstant {

	String C_ENTITY = "Entity";

	String C_SERVICE = "Service";

	String C_SERVICE_IMPL = "ServiceImpl";

	String C_MAPPER = "Mapper";

	String C_CONTROLLER = "Controller";

	String DISABLE_STR = "disable";

	String ENABLE_STR = "enable";

	String TRUE_STR = "true";

	String FILE_SPLIT_STR = ",";

	String OPT_ADMIN_USER = "admin";

	String SETTING_AUTHOR = "author";

	String SETTING_TABLE_PREFIX = "tablePrefix";

	String SETTING_PERSISTENT_METHOD = "persistentMethod";

	String SETTING_PERSISTENT_METHOD_GITLAB = "GitLab";

	String SETTING_PERSISTENT_METHOD_FILE = "File";

	String SETTING_IS_PERSISTENT = "isPersistent";

	String SETTING_USER_PATH = "user";

	String SETTING_PARENT = "parent";

	String SETTING_AUTO_GENERATOR_PATH = "AutoGeneratorPath";

	String SETTING_MAVEN_COMPILE_METHOD = "compile";

	String SETTING_RESULT = "result";

	String SETTING_PARENT_ENTITY = "parentEntity";

	String SETTING_PARENT_CONTROLLER = "parentController";

	String SETTING_JDBC_URL = "jdbc.url";

	String SETTING_JDBC_USERNAME = "jdbc.username";

	String SETTING_JDBC_PASSWORD = "jdbc.password";

	String SETTING_JDBC_DRIVER = "jdbc.driver";

	String SETTING_TEMPLATES_APPLICATION = "templates.application";

	String SETTING_TEMPLATES_CONTROLLER = "templates.controller";

	String SETTING_TEMPLATES_ENTITY = "templates.entity";

	String SETTING_TEMPLATES_MAPPER = "templates.mapper";

	String SETTING_TEMPLATES_XML = "templates.xml";

	String SETTING_TEMPLATES_SERVICE = "templates.service";

	String SETTING_TEMPLATES_SERVICE_IMPL = "templates.serviceImpl";

	String SETTING_TEMPLATES_BASE_CONTROLLER = "templates.baseController";

	String SETTING_TEMPLATES_BASE_ENTITY = "templates.baseEntity";

	String SETTING_TEMPLATES_RESULT = "templates.result";

	String SETTING_TEMPLATES_POM = "templates.pom";

	String SETTING_CLASS_PATH = "classPath";

	String SETTING_MAVEN_PATH1 = "mavenPath1";

	String SETTING_MAVEN_PATH2 = "mavenPath2";

	String SETTING_JAVA_HOME1 = "javaHome1";

	String SETTING_JAVA_HOME2 = "javaHome2";

	String SETTING_GITLAB_TREE = "gitlab.tree";

	String SETTING_GITLAB_USERNAME = "gitlab.username";

	String SETTING_GITLAB_PASSWORD = "gitlab.password";

	String SETTING_GITLAB_HTTP = "gitlab.http";

	String SETTING_GITLAB_PROJECT = "gitlab.project";

	String SETTING_GITLAB_BRANCH = "gitlab.branch";

	String SETTING_PACKAGE_CONTROLLER = "controller";

	String SETTING_PACKAGE_SERVICE = "service";

	String SETTING_PACKAGE_SERVICE_IMPL = "service.impl";

	String SETTING_PACKAGE_ENTITY = "entity";

	String SETTING_PACKAGE_MAPPER = "mapper";

	String SETTING_JAVA_FILE_EXTENSION = ".java";

	String SETTING_FTL_FILE_EXTENSION = ".ftl";

	String SETTING_FILE_POM_XML = "pom.xml";

	String DEFAULT_TEMPLATES_PATH = "/defaultTemplates";

	String FILE_NAME_CONTROLLER = "/controller.java";

	String FILE_NAME_ENTITY = "/entity.java";

	String FILE_NAME_MAPPER = "/mapper.java";

	String FILE_NAME_SERVICE = "/service.java";

	String FILE_NAME_SERVICE_IMPL = "/serviceImpl.java";

	String FILE_NAME_MAPPER_XML = "/mapper.xml";

	String FILE_NAME_BASE_CONTROLLER = "/BaseController.java";

	String FILE_NAME_BASE_ENTITY = "/BaseEntity.java";

	String FILE_NAME_RESULT = "/ResultMsg.java";

	String MESSAGE_ERROR_TABLE_EXIST = "数据表已存在！";

	String MESSAGE_ERROR_TABLE_NOT_EXIST = "数据表不存在，请先生成表！";

	String MESSAGE_ERROR_FIELD_EXIST = "新字段不能与原有其他字段名重名！";

	String MESSAGE_ERROR_FIELD_NOT_EXIST = "请给出相关需要的字段信息，字段信息不能为空！";

	String MESSAGE_ERROR_TABLE_ALREADY_USE = "该表已由其他用户占用，无权操作，请更换表名！";

	String MESSAGE_ERROR_TABLE_REMOVE = "清除表失败！";

	String MESSAGE_ERROR_TABLE_COLUMN_NOT_MATCHES = "字段名不允许出现除大小写字母、数字、下划线之外的字符，且不允许数字开头！";

	String MESSAGE_ERROR_TABLE_COLUMN_NOT_MATCHES2 = "字段注释中不允许出现英文的反斜杠、双引号等字符！";

	String MESSAGE_ERROR_GENERATOR_CODE_CREAT_FILE = "业务层模板文件创建失败！";

	String MESSAGE_ERROR_ADD_REQUEST_MAPPING = "RequestMappingHandlerMapping中已存在该接口：";

	String MESSAGE_ERROR_ADD_BEAN = "不存在或加载异常！";

	String MESSAGE_ERROR_NOT_AUTH = "无权限操作！";

	String MESSAGE_ERROR_CLEAN_DIR = "清空目录失败！";

	String MESSAGE_ERROR_REMOVE_GENERATOR_URL = "接口移除失败！";

	String MESSAGE_ERROR_ADD_GENERATOR_URL_NOT_TABLE = "表信息不存在，请删除相关代码文件后重新生成！";

	String MESSAGE_ERROR_GENERATOR_ADD_URL = "接口第一次尝试添加失败！";

	String MESSAGE_ERROR_GENERATOR_SECOND_ADD_URL = "接口第二次尝试添加失败！";

	String MESSAGE_ERROR_COMPILE = "编译失败，请检查相关模板是否正确！移除相关代码文件！";

	String MESSAGE_ERROR_COMPILE_EXIT_CODE = "编译退出代码出错！";

	String MESSAGE_ERROR_COMPILE_MAVEN_PATH = "Maven目录配置无效！";

	String MESSAGE_ERROR_GENERATOR_GITLAB_ADD = "上传至GitLab失败！";

	String MESSAGE_ERROR_GENERATOR_CODE_VAR_MAP = "模板变量转换合并失败，请检查内容是否为标准的JSON格式！";

	String MESSAGE_ERROR_CREAT_DEFAULT_TEMPLATE_FILE = "创建默认文件失败！清除模板目录！";

	String MESSAGE_ERROR_CREAT_DEFAULT_TEMPLATE_DIR = "创建默认模板目录失败！";

	String MESSAGE_ERROR_CREAT_TEMP_TEMPLATE_DIR = "创建临时模板目录失败！";

	String MESSAGE_ERROR_COPY_COMMON_FILE = "复制通用文件application、pom.xml失败！";

	String MESSAGE_ERROR_CLEAN_TEMP_DIR = "清空临时目录失败！";

	String MESSAGE_ERROR_JSON_PARSE = "JSON信息解析失败！";

	String MESSAGE_ERROR_GENERATOR_TABLE = "创建数据表失败！";

	String MESSAGE_ERROR_UPDATE_JSON_PARSE = "UpdateJSON信息解析失败！";

	String MESSAGE_ERROR_GENERATOR_CODE = "创建业务层代码失败！";

	String MESSAGE_ERROR_GENERATOR_COMPILE = "编译失败！";

	String MESSAGE_ERROR_GENERATOR_LOAD_START = "表未被创建，或未生成并编译相关代码！";

	String MESSAGE_ERROR_GENERATOR_LOAD_EXIST = "动态代码加载失败，内存已存在bean，不可重复加载！";

	String MESSAGE_ERROR_GENERATOR_LOAD = "动态代码加载失败！";

	String MESSAGE_ERROR_REMOVE_GENERATOR_BEAN = "Bean不存在或删除失败，移除Bean失败！";

	String MESSAGE_ERROR_REMOVE_GENERATOR_TABLE = "移除相关表失败！";

	String MESSAGE_ERROR_REMOVE_GENERATOR_CODE = "移除相关代码成功！相关代码路径：";

	String MESSAGE_ERROR_REMOVE_ALL_GENERATOR = "清除所有代码生成器失败！";

	String MESSAGE_ERROR_OPT_URL_OPEN = "接口启用失败，请检查代码生成器是否已经注入该模块！";

	String MESSAGE_ERROR_OPT_URL_CLOSE = "接口禁用失败，请检查代码生成器是否已经注入该模块！";

	String MESSAGE_ERROR_OPT_URL_TYPE = "请输入正确的操作类型！";

	String MESSAGE_ERROR_TYPE_NOT_EXIST = "字段类型不存在：";

	String MESSAGE_ERROR_TYPE_IS_NULL = "字段类型不能为空！";

	String MESSAGE_ERROR_NAME_IS_NULL = "字段名不能为空！";

	String MESSAGE_ERROR_UPDATE_JSON_IS_NULL = "更新JSON中不能包含空元素！";

	String MESSAGE_ERROR_UPDATE_JSON_IS_DIFFER = "更新JSON中字段信息和所有字段信息json中对应的字段信息不一致！";

	String MESSAGE_SUCCESS_URL_PARSE = "Url解码完成！";

	String MESSAGE_SUCCESS_UPDATE_URL_PARSE = "UpdateJSON解码完成！";

	String MESSAGE_SUCCESS_GITLAB_REMOVE = "删除GitLab目录成功：";

	String MESSAGE_SUCCESS_GENERATOR = "代码生成器执行完成！";

	String MESSAGE_SUCCESS_GENERATOR_ADD_URL = "接口添加成功！";

	String MESSAGE_SUCCESS_GENERATOR_SECOND_ADD_URL = "接口第二次尝试添加成功！";

	String MESSAGE_SUCCESS_GENERATOR_GITLAB_ADD = "上传至GitLab成功！";

	String MESSAGE_SUCCESS_GENERATOR_CODE = "创建业务层代码成功，项目根路径：";

	String MESSAGE_SUCCESS_CREAT_TEMP_TEMPLATE_DIR = "删除临时目录成功！";

	String MESSAGE_SUCCESS_GENERATOR_TABLE = "生成数据库表完成，数据库表名为：";

	String MESSAGE_SUCCESS_GENERATOR_COMPILE = "编译成功！";

	String MESSAGE_SUCCESS_GENERATOR_LOAD = "加载Class结果：";

	String MESSAGE_SUCCESS_REMOVE_GENERATOR_BEAN = "移除注入Bean成功！";

	String MESSAGE_SUCCESS_REMOVE_GENERATOR_TABLE = "移除相关表成功！表名为：";

	String MESSAGE_SUCCESS_REMOVE_GENERATOR = "移除相关代码生成器执行完成！";

	String MESSAGE_SUCCESS_REMOVE_ALL_GENERATOR = "清除所有代码生成器成功！";

	String MESSAGE_SUCCESS_OPT_URL_OPEN = "接口启用成功！";

	String MESSAGE_SUCCESS_OPT_URL_CLOSE = "接口禁用成功！";

	String MESSAGE_INFO_GITLAB_REMOVE = "删除GitLab上生成的代码！";

	String MESSAGE_INFO_GENERATOR_SECOND_ADD_URL = "接口尝试二次添加！";

	String MESSAGE_INFO_BEAN_REMOVE = "移除Bean：";

	String MESSAGE_INFO_GENERATOR_SRE_MOVE_PATH = "移除相关代码路径：";

	String MESSAGE_INFO_COMPILE_THREAD = "启用线程重新编译！";

	String MESSAGE_INFO_GENERATOR_GITLAB_ADD_COMMIT = "AutoCreate GeneratorCode：";

	String MESSAGE_INFO_CREAT_DEFAULT_TEMPLATE_DIR = "创建默认模板目录：";

	String MESSAGE_INFO_CREAT_TEMP_TEMPLATE_DIR = "创建临时模板目录：";

	String MESSAGE_INFO_CREAT_TABLE = "创建表：";

	String MESSAGE_INFO_UPDATE_COLUMN = "更新表字段SQL：";

	String MESSAGE_INFO_DELETE_COLUMN = "删除表字段SQL：";

	String MESSAGE_INFO_ADD_COLUMN = "添加表字段SQL：";

	String MESSAGE_INFO_GENERATOR_START = "开始执行：";

	String MESSAGE_INFO_FIELD_NOT_CHANGE = "更新内容未发生任何变化！";

	String MESSAGE_INFO_URL_PARSE = "Url解码！";

	String MESSAGE_INFO_GENERATOR_TABLE = "正在生成表！";

	String MESSAGE_INFO_UPDATE_URL_PARSE = "UpdateJSON解码！";

	String MESSAGE_INFO_GENERATOR_COMPILE = "开始编译！";

	String MESSAGE_INFO_REMOVE_GENERATOR_CODE = "移除相关代码路径：";

}
