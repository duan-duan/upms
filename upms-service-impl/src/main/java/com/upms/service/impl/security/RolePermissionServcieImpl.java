/**
 * 
 */
package com.upms.service.impl.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;
import com.common.model.Tree;
import com.common.service.impl.BaseServiceImpl;
import com.upms.dao.security.ResourceMapper;
import com.upms.dao.security.RolePermissionMapper;
import com.upms.entity.security.Resource;
import com.upms.entity.security.RolePermission;
import com.upms.entity.utils.ResourceTreeUtils;
import com.upms.service.api.security.RolePermissionService;

/**
 * @author zhanghaiyang
 * 角色与权限关联表的Service
 */
@Service
public class RolePermissionServcieImpl<T extends BaseEntity, ID extends Serializable>
extends BaseServiceImpl<T, ID> implements RolePermissionService<T, ID>{

	@Autowired
	private RolePermissionMapper<T, ID> rolePermissionMapper;
	
	@Autowired
	private ResourceMapper<Resource, Long> resourceMapper;
	
	
	@Override
	public BaseMapper<T, ID> getMapper() {
		return rolePermissionMapper;
	}
	

	/**
	 * 通过系统ID获取对应角色权限
	 * @param srId
	 * @param sysId
	 * @return
	 */
	@Override
	public List<RolePermission> getRolePermissionListBySrId(Integer srId,Integer sysId) {
		return rolePermissionMapper.getListBySrId(srId,sysId);
	}

	/**
	 * 通过系统ID获取对应角色权限
	 * @param srId
	 * @param sysId
	 * @return
	 */
	@Override
	public List<RolePermission> getListByEntityAndSysId(long spId, long srId,
			long sysId) {
		return rolePermissionMapper.getListByEntityAndSysId(spId, srId, sysId);
	}
	
	/**
	 * 生成角色对应的系统树
	 * @param roleId 角色ID
	 * @param sysId  系统ID
	 * @return
	 */
	public List<Tree> getRolePermissionListByRoleIdAndSysId(Integer roleId,Integer sysId){
		
		//获取系统资源
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("sysId", sysId);
		List<Resource> sysResourceList = resourceMapper.getAllMenuList(param);
		
		//获取角色对应的资源列表
		param = new HashMap<String,Object>();
		param.put("roleId", roleId);
		param.put("sysId", sysId);
		List<Resource> roleResourceList = resourceMapper.getAuthorizedList(param);
		List<Long> treeCheckedIds = new ArrayList<Long>();
		for (Resource resource:roleResourceList) {
			treeCheckedIds.add(resource.getSp_id());
		}
		
		return ResourceTreeUtils.convertResourceListToTreeList(sysResourceList, treeCheckedIds);
	}

	/**
	 * 获取角色已经授权的菜单权限列表
	 * @param roleId 角色Id
	 * @return List<RolePermission>
	 */
	@Override
	public List<RolePermission> getPermissionMenuListForRole(Long roleId) {
		return rolePermissionMapper.getPermissionMenuListForRole(roleId);
	}

	/**
	 * 获取某个界面，某个角色授权功能按钮列表
	 * @param roleId 角色Id
	 * @param pageId 页面Id
	 * @return
	 */
	@Override
	public List<RolePermission> getPermissionButtonListForPage(Long roleId,Long pageId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("srId", roleId);
		param.put("spId", pageId);
		return rolePermissionMapper.getPermissionButtonListForPage(param);
	}

	/**
	 * 保存菜单权限
	 * @param  roleId  角色Id
	 * @param  menuIds 选中的菜单列表
	 * @return boolean 成功或失败
	 */
	@Override
	public boolean storeRolePermission(Long roleId, List<Long> menuIds) {
		//查询出角色当前所有的菜单权限
		//遍历当前，并与新授权菜单权限进行比较，如果{1.当前有，新授权有，则不变；2.当前有，新授权没有，则要删除，如果该节点page，则同时删除相应的功能授权；3，当前没有，新授权有，则新增授权信息}
		//可以通过保留按钮，删除所有
		
		 List<RolePermission> currentRolePermissions = getPermissionMenuListForRole(roleId);
		 List<RolePermission> btnPermissions = new ArrayList<RolePermission>();
		 
		 for (RolePermission permission : currentRolePermissions) {
			 if (menuIds.contains(permission.getSpId())) {
				 if ("page".equals(permission.getSpType())) {
					 btnPermissions.addAll(getPermissionButtonListForPage(roleId,permission.getSpId()));
				 }
			 } 
		 }
		 
		 //删除角色下的权限
		 rolePermissionMapper.deleteById((ID)roleId);
		 
		 //保存权限
		 for (Long menuId : menuIds) {
			 RolePermission rolePermission = new RolePermission();
			 rolePermission.setSpId(menuId);
			 rolePermission.setSrId(roleId);
			 rolePermissionMapper.createEntity((T)rolePermission);
		 }
		 
		 for(RolePermission permission :btnPermissions) {
			 rolePermissionMapper.createEntity((T)permission);
		 }
		
		return true;
	}
}
