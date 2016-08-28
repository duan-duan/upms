package com.upms.service.api.security;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.common.entity.BaseEntity;
import com.common.service.BaseService;
import com.upms.entity.security.Role;

public interface UserRoleService<T extends BaseEntity, ID extends Serializable>
		extends BaseService<T, ID> {
	/**
	 * 根据用户ID查找用户的角色
	 */
	List<Role> getRolesByUserId(Map<String, String> userinfo);

	/**
	 * 根据用户ID查找用户未有的角色
	 */
	List<Role> getRolesByNotUserId(Map<String, String> userinfo);

	public Map<String, List<Role>> selectRoleManager(String userId,String currentUserAccount);

	public void saveOrUpdateUserRoles(String userId, String roleIds);
	
	/**
	 * @return
	 * wangzaifei
	 */
	public List<Map<String, Object>> queryMasLocList();
	/**
	 * 查询承运商
	 */
	public List<Map<String, Object>> queryLsPList();
	
}
