/**
 * 
 */
package com.upms.service.impl.security;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;
import com.common.service.impl.BaseServiceImpl;
import com.upms.dao.security.SystemModelMapper;
import com.upms.service.api.security.SystemModelService;

/**
 * @author zhanghaiyang
 * @param <SystemModelMapper>
 * 系统模型Service
 */
@Service
public class SystemModelServiceImpl <T extends BaseEntity, ID extends Serializable> extends BaseServiceImpl<T, ID> implements SystemModelService<T, ID>{
	
	@Autowired
	private SystemModelMapper<T, ID> systemModelMapper;
	
	@Override
	public BaseMapper<T, ID> getMapper() {
		return systemModelMapper;
	}
}
