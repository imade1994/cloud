package com.tj.cloud.uid.worker.entity;

import com.tj.cloud.core.utils.ToStringUtils;
import com.tj.cloud.uid.worker.WorkerNodeType;

import java.io.Serializable;
import java.util.Date;

/**
 * * @Author codingMan_tj
 * * @Date 2024/4/1 13:32
 * * @version v1.0.0
 * * @desc 机器实体
 **/
public class WorkerNodeEntity extends ToStringUtils implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * Entity unique id (table unique)
     */
    private long id;

    /**
     * Type of CONTAINER: HostName, ACTUAL : IP.
     */
    private String hostName;

    /**
     * Type of CONTAINER: Port, ACTUAL : Timestamp + Random(0-10000)
     */
    private String port;

    /**
     * type of {@link WorkerNodeType}
     */
    private int type;

    /**
     * Worker launch date, default now
     */
    private Date launchDate = new Date();

    /**
     * Created time
     */
    private Date created;

    /**
     * Last modified
     */
    private Date modified;


}
