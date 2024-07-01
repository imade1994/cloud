
package com.tj.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tj.cloud.entity.DynamicModel;
import org.apache.ibatis.annotations.*;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/14
 * @Description:
 * @version:1.0
 */
@Mapper
public interface DynamicModelMapper extends BaseMapper<DynamicModel> {

	/**
	 * 指定表名，删除指定的表
	 * @param tableName 表名
	 * @return void
	 */
	@Update("drop table if exists ${tableName}")
	void dropTable(String tableName);

	/**
	 * 根据给定的sql语句创建表
	 * @param sql SQL建表语句
	 * @return void
	 */
	@Update("${sql}")
	void createTable(String sql);

	/**
	 * 给定表名，从数据库中查询是否存在该表，返回表名
	 * @param tableName 表名
	 * @return void
	 */
	@Select("select TABLE_NAME from information_schema.tables where table_name =(#{tableName})")
	String ifTableIsCreated(String tableName);

	/**
	 * 给定sql语句，删除除相关表中的字段
	 * @param sql SQL删除表字段语句
	 * @return void
	 */
	@Delete("${sql}")
	void deleteFields(String sql);

	/**
	 * 给定sql语句，添加除相关表中的字段
	 * @param sql SQL添加表字段语句
	 * @return void
	 */
	@Insert("${sql}")
	void addFields(String sql);

	/**
	 * 给定sql语句，修改除相关表中的字段
	 * @param sql SQL修改表字段语句
	 * @return void
	 */
	@Update("${sql}")
	void updateFields(String sql);

}
