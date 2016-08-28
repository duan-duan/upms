/**
 * 
 */
package com.upms.dao.security;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;
import com.upms.entity.security.Role;

/**
 * @author zhanghaiyang
 *
 */
public interface RoleMapper<T extends BaseEntity, ID extends Serializable>
extends BaseMapper<T, ID> {
	List<Role> getAllRolesByUser(@Param("account") String account);
}
