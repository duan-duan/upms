/**
 * 
 */
package com.upms.dao.security;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;
import com.upms.entity.security.Role;

/**
 * @author ouyang
 * 
 */
public interface UserRoleMapper<T extends BaseEntity, ID extends Serializable> extends BaseMapper<T, ID> {
	/**
	 * 根据用户ID查找用户的角色
	 */
	List<Role> getRolesByUserId(Map<String, String> userinfo);

	/**
	 * 根据用户ID查找用户未有的角色
	 */
	List<Role> getRolesByNotUserId(Map<String, String> userinfo);

	/**
	 * 删除用户角色
	 */
	public void deleteRoleByUser(Map<String, Object> userRoles);

	/**
	 * 添加用户角色
	 */
	public void insertRolesForUser(Map<String, Object> userRoles);
	
	/**
	 * 
	 * 查询仓库
	 * wangzaifei
	 */
	public List<Map<String, Object>> queryMasLocList();

	/**
	 * 查询承运商
	 */
	public List<Map<String, Object>> queryLsPList();
}
