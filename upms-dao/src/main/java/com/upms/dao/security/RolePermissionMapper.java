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
import com.upms.entity.security.RolePermission;

/**
 * @author zhanghaiyang
 *
 */
public interface RolePermissionMapper<T extends BaseEntity, ID extends Serializable>
extends BaseMapper<T, ID> {
	
	List<RolePermission> getListBySrId(@Param("srId")int srId,@Param("sysId")int sysId);
	
	List<RolePermission> getListByEntityAndSysId(@Param("spId")long spId,@Param("srId")long srId,@Param("sysId")long sysId);
	
	/**
	 * 获取某个界面，某个角色授权功能按钮列表 
	 * @param map
	 *        key-value: srId - 角色Id
	 *                   spId - pageId
	 * @return
	 */
	List<RolePermission> getPermissionButtonListForPage(Map map);
	
	/**
	 * 获取某个角色授权菜单列表
	 * @param srId 角色Id
	 * @return
	 */
	List<RolePermission> getPermissionMenuListForRole(@Param("srId")Long srId);
}
