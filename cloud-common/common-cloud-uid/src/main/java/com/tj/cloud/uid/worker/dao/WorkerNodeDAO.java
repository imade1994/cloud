package com.tj.cloud.uid.worker.dao;

import com.tj.cloud.uid.worker.entity.WorkerNodeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * * @Author codingMan_tj * @Date 2024/4/1 13:32 * @version v1.0.0 * @desc
 **/
@Repository
public interface WorkerNodeDAO {

	/**
	 * Get {@link WorkerNodeEntity} by node host
	 * @param host
	 * @param port
	 * @return
	 */
	WorkerNodeEntity getWorkerNodeByHostPort(@Param("host") String host, @Param("port") String port);

	/**
	 * Add {@link WorkerNodeEntity}
	 * @param workerNodeEntity
	 */
	void addWorkerNode(WorkerNodeEntity workerNodeEntity);

}
