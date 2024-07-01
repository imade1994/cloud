package com.tj.cloud.uid.worker;

import com.tj.cloud.uid.utils.DockerUtils;
import com.tj.cloud.uid.utils.NetUtils;
import com.tj.cloud.uid.worker.dao.WorkerNodeDAO;
import com.tj.cloud.uid.worker.entity.WorkerNodeEntity;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * * @Author codingMan_tj * @Date 2024/4/1 13:29 * @version v1.0.0 * @desc
 **/
public class DisposableWorkerIdAssigner implements WorkerIdAssigner {

	private static final Logger LOGGER = LoggerFactory.getLogger(DisposableWorkerIdAssigner.class);

	@Resource
	private WorkerNodeDAO workerNodeDAO;

	/**
	 * Assign worker id base on database.
	 * <p>
	 * If there is host name & port in the environment, we considered that the node runs
	 * in Docker container<br>
	 * Otherwise, the node runs on an actual machine.
	 * @return assigned worker id
	 */
	@Transactional
	public long assignWorkerId() {
		// build worker node entity
		WorkerNodeEntity workerNodeEntity = buildWorkerNode();

		// add worker node for new (ignore the same IP + PORT)
		workerNodeDAO.addWorkerNode(workerNodeEntity);
		LOGGER.info("Add worker node:" + workerNodeEntity);

		return workerNodeEntity.getId();
	}

	/**
	 * Build worker node entity by IP and PORT
	 */
	private WorkerNodeEntity buildWorkerNode() {
		WorkerNodeEntity workerNodeEntity = new WorkerNodeEntity();
		if (DockerUtils.isDocker()) {
			workerNodeEntity.setType(WorkerNodeType.CONTAINER.value());
			workerNodeEntity.setHostName(DockerUtils.getDockerHost());
			workerNodeEntity.setPort(DockerUtils.getDockerPort());

		}
		else {
			workerNodeEntity.setType(WorkerNodeType.ACTUAL.value());
			workerNodeEntity.setHostName(NetUtils.getLocalAddress());
			workerNodeEntity.setPort(System.currentTimeMillis() + "-" + RandomUtils.nextInt(100000));
		}

		return workerNodeEntity;
	}

}
