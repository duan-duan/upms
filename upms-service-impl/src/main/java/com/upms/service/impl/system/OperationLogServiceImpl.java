/**
 * 
 */
package com.upms.service.impl.system;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;
import com.common.service.impl.BaseServiceImpl;
import com.upms.dao.system.OperationLogMapper;
import com.upms.service.api.system.OperationLogService;

/**
 * @author zhanghaiyang
 * 操作日志Service
 */
@Service
public class OperationLogServiceImpl<T extends BaseEntity, ID extends Serializable> extends BaseServiceImpl<T, ID> implements OperationLogService<T, ID> {

	@Autowired
	private OperationLogMapper<T, ID> operationLogMapper;
	
	@Override
	public BaseMapper<T, ID> getMapper() {
		return operationLogMapper;
	}
	
}
