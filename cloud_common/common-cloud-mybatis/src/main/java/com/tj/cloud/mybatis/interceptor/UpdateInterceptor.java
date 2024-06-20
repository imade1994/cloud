package com.tj.cloud.mybatis.interceptor;

import cn.hutool.core.util.ArrayUtil;
import com.tj.cloud.core.context.ICurrentContext;
import com.tj.cloud.core.model.CreateInfoModel;
import com.tj.cloud.core.model.IdModel;
import com.tj.cloud.mybatis.base.BaseModel;
import com.tj.cloud.uid.UidGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/29 17:54
 * * @version v1.0.0
 * * @desc
 **/
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "insert", args = {MappedStatement.class, Object.class})
})

public class UpdateInterceptor implements Interceptor {

    @Resource
    ICurrentContext currentContext;

    @Resource
    UidGenerator uidGenerator;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        if (ArrayUtil.isEmpty(args) || args.length < 2) {
            return invocation.proceed();
        }

        Object param = args[1];
        MappedStatement statement = (MappedStatement) args[0];
        // 更新逻辑
        if (statement.getId().endsWith(".update") || statement.getId().contains("update")) {
            if (param instanceof CreateInfoModel) {
                CreateInfoModel model = (CreateInfoModel) param;
                    model.setUpdateTime(LocalDateTime.now());
                model.setUpdateBy(currentContext.getCurrentUserId());
                if (param instanceof BaseModel) {
                    BaseModel baseModel = (BaseModel) param;
                    baseModel.setUpdateBy(currentContext.getCurrentUserId());
                }
            }
        }

        //新增逻辑
        else if (StringUtils.endsWithAny(statement.getId(), ".create", ".insertSelective")) {
            //为ID赋值
            if (param instanceof IdModel) {
                IdModel model = (IdModel) param;
                if (model.getId() == null || model.getId().isEmpty()) {
                    model.setId(String.valueOf(uidGenerator.getUID()));
                }
            }
            //创建信息赋值
            if (param instanceof CreateInfoModel) {
                CreateInfoModel model = (CreateInfoModel) param;
                if (model.getCreateTime() == null) {
                    model.setCreateTime(LocalDateTime.now());
                    model.setCreateBy(currentContext.getCurrentUserId());
                }
                if (model.getUpdateTime() == null) {
                    model.setUpdateTime(LocalDateTime.now());
                    model.setUpdateBy(currentContext.getCurrentUserId());
                }
            }
        }

        // 批量新增

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
