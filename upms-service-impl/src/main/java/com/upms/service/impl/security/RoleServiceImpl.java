package com.upms.service.impl.security;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;
import com.common.service.impl.BaseServiceImpl;
import com.upms.dao.security.RoleMapper;
import com.upms.service.api.security.RoleService;

@Service
public class RoleServiceImpl<T extends BaseEntity, ID extends Serializable>
extends BaseServiceImpl<T, ID> implements RoleService<T, ID>{

	@Autowired
	RoleMapper<T, ID> roleMapper;

	@Override
	public BaseMapper<T, ID> getMapper() {
		return roleMapper;
	}
	
}
