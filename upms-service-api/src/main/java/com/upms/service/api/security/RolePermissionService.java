/**
 * 
 */
package com.upms.service.api.security;

import java.io.Serializable;
import java.util.List;

import com.common.entity.BaseEntity;
import com.common.model.Tree;
import com.common.service.BaseService;
import com.upms.entity.security.RolePermission;

/**
 * @author zhanghaiyang
 * 角色与权限关联表的Service
 */
public interface RolePermissionService<T extends BaseEntity, ID extends Serializable> extends BaseService<T, ID> {

	/**
	 * 通过系统ID获取对应角色权限
	 * @param srId
	 * @param sysId
	 * @return
	 */
	public List<RolePermission> getRolePermissionListBySrId(Integer srId,Integer sysId);
	
	/**
	 * 通过系统ID获取对应角色权限
	 * @param srId
	 * @param sysId
	 * @return
	 */
	public List<RolePermission> getListByEntityAndSysId(long spId,long srId,long sysId);
	
	/**
	 * 生成角色对应的系统树
	 * @param roleId 角色ID
	 * @param sysId  系统ID
	 * @return
	 */
	List<Tree> getRolePermissionListByRoleIdAndSysId(Integer roleId,Integer sysId);
	
	/**
	 * 获取角色已经授权的菜单权限列表
	 * @param roleId 角色Id
	 * @return List<RolePermission>
	 */
	public List<RolePermission> getPermissionMenuListForRole(Long roleId);
	
	/**
	 * 获取某个界面，某个角色授权功能按钮列表
	 * @param roleId 角色Id
	 * @param pageId 页面Id
	 * @return
	 */
	public List<RolePermission> getPermissionButtonListForPage(Long roleId,Long pageId);
	
	/**
	 * 保存菜单权限
	 * @param  roleId  角色Id
	 * @param  menuIds 选中的菜单列表
	 * @return boolean 成功或失败
	 */
	public boolean storeRolePermission(Long roleId,List<Long> menuIds);
}
