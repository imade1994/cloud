/*
 * Copyright (c) 2003-2021 www.hualongxunda.com/ Inc. All rights reserved.
 * 注意：本内容仅限于深圳华龙讯达信息技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.tj.cloud.constant;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/18
 * @Description:
 * @version:1.0
 */
public interface TemplateConstant {


    String C_ENTITY = "Entity";
    String C_SERVICE = "Service";
    String C_SERVICE_IMPL = "ServiceImpl";
    String C_MAPPER = "Mapper";
    String C_VAR = "Var";
    String C_CONTROLLER = "Controller";
    String C_ALL = "All";

    String T_OPT_GET = "get";
    String T_OPT_ENABLE = "enable";
    String T_OPT_DISABLE = "disable";
    String IS_USE_STR = "isUse";

    String T_ERROR_TYPE_MESSAGE = "请输入正确的模板类型！";
    String T_OPT_ERROR_TYPE_MESSAGE = "请输入正确的操作类型！";
    String T_OPT_ENABLE_ERROR_MESSAGE = "启用模板操作失败！";
    String T_OPT_DISABLE_ERROR_MESSAGE = "禁用模板操作失败！";

    String MESSAGE_ERROR_NOT_FOUND_TEMPLATES = "未找到该模板！";
    String MESSAGE_ERROR_DELETE_TEMPLATES = "模板删除失败！";
    String MESSAGE_ERROR_SAVE_TEMPLATES = "代码模板保存失败！";

    String MESSAGE_SUCCESS_DELETE_TEMPLATES = "模板删除成功！";
    String MESSAGE_SUCCESS_SAVE_TEMPLATES = "代码模板保存成功！";
    String MESSAGE_SUCCESS_OPT_TEMPLATES = "操作成功！";

    String MESSAGE_INFO_DELETE_TEMPLATES = "模板删除成功！";
}
