package com.upms.service.impl.system;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;
import com.common.service.impl.BaseServiceImpl;
import com.upms.dao.system.LoginFailMapper;
import com.upms.service.api.system.LoginFailService;

/**
 * 
 * @author guoqiushi
 *
 */
@Service
public class LoginFailServiceImpl<T extends BaseEntity, ID extends Serializable>
extends BaseServiceImpl<T, ID> implements LoginFailService<T, ID>{
	
	@Autowired
	LoginFailMapper<T, ID> loginFailMapper;

	@Override
	public BaseMapper<T, ID> getMapper() {
		return loginFailMapper;
	}
}
