/**
 * 
 */
package com.upms.service.api.security;

import java.io.Serializable;
import java.util.List;

import com.common.entity.BaseEntity;
import com.common.model.Tree;
import com.common.service.BaseService;
import com.upms.entity.security.Permission;

/**
 * @author zhanghaiyang
 * 权限Service
 */
public interface PermissionService<T extends BaseEntity, ID extends Serializable> extends
BaseService<T, ID>  {
	
	/**
	 * 获取所有权限树形信息，不包含默认勾选
	 * @param sysId
	 * @return
	 */
	public List<Tree> getPermissionTreeList(long sysId);
	
	
	/**
	 * 获取所有权限树形信息，包含默认勾选
	 * @param defaultSpIdStrList
	 * @param sysId
	 * @return
	 */
	public List<Tree> getPermissionTreeListContainsDefault(List<String> defaultSpIdStrList,long sysId);

	/**
	 * 通过父节点ID获取权限按钮
	 * @param parentId
	 * @param sysId
	 * @return
	 */
	public List<Permission> getPermissionButtonListByParentId(Long parentId,Long sysId);
	
	/**
	 * 获取某用户所有权限列表
	 * @param account
	 * @param sysId
	 * @return
	 */
	public List<Permission> getAllPermissionForUser(String account,Long sysId);
	
}
