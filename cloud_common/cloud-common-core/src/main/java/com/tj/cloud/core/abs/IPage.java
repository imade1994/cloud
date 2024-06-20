package com.tj.cloud.core.abs;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/25 14:13
 * * @version v1.0.0
 * * @desc 抽象分页接口
 **/
public interface IPage {

    /**
     * 默认每页显示的记录数
     */
    int DEFAULT_PAGE_SIZE = 10;

    /**
     * 返回每页大小
     * @return 返回每页大小
     */
    Integer getPageSize();

    /**
     * 返回总页数
     * @return 返回总页数
     */
    Integer getTotal();

    /**
     * 返回总页码
     * @return 返回总页码
     */
    Integer getPageNo();

    /**
     * 是否显示总记录数
     * @return 是否显示总记录数
     */
    boolean isShowTotal();

    /**
     * 获取当前页的偏移量
     * @return 获取当前页的偏移量
     */
    Integer getStartIndex();
}
