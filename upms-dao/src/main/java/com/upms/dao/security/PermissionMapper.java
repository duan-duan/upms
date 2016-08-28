/**
 * 
 */
package com.upms.dao.security;

import java.io.Serializable;
import java.util.List;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;
import com.upms.entity.security.Permission;

import org.apache.ibatis.annotations.Param;

/**
 * @author zhanghaiyang
 *
 */
public interface PermissionMapper<T extends BaseEntity, ID extends Serializable>
extends BaseMapper<T, ID>  {
	
	List<Permission> getPermissionButtonListByParentId(@Param("parentId")Long parentId,@Param("sysId")Long sysId);
	
	/**
	 * 根据账号查到该用户的所有权限
	 * @param account
	 * @return
	 */
	List<Permission> getAllPermissionByUserAccount(@Param("account") String account,@Param("sysId") Long sysId);
	
	List<Permission> getPermissionByRoleId(@Param("srId") Long srId,@Param("sysId") Long sysId);
	
	
	
}
