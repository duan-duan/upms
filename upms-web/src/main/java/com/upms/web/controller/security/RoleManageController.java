/**
 * 
 */
package com.upms.web.controller.security;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.service.BaseService;
import com.common.web.controller.BaseCRUDAction;
import com.upms.entity.security.Permission;
import com.upms.entity.security.Role;
import com.upms.entity.security.RolePermission;
import com.upms.entity.security.SystemModel;
import com.upms.service.api.security.PermissionService;
import com.upms.service.api.security.RolePermissionService;
import com.upms.service.api.security.RoleService;
import com.upms.service.api.security.SystemModelService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * 角色管理模块
 * @author zhanghaiyang
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/roleManage")
public class RoleManageController extends BaseCRUDAction<Role, Integer> {

	@Autowired
	RoleService<Role, Integer> roleService;

	@Autowired
	SystemModelService<SystemModel, Integer> systemModelService;

	@Autowired
	PermissionService<Permission, Integer> permissionService;

	@Autowired
	RolePermissionService<RolePermission, Integer> rolePermissionService;

	@Override
	public BaseService<Role, Integer> getBaseService() {
		return roleService;
	}

	/**
	 * 加载下拉列表：系统信息表
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "sysList", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getSysList(HttpServletResponse response) {
		List<Map<String, Object>> list = Lists.newArrayList();
		try {

			List<SystemModel> queryList = systemModelService.queryList();
			if ((null != queryList) && (queryList.size() > 0)) {
				for (SystemModel systemModel : queryList) {
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", systemModel.getId());
					map.put("name", systemModel.getName());
					list.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
	/**
	 * 加载某节点的按钮菜单
	 * @param response
	 * @param parentId
	 * @param srId
	 * @param sysId
	 * @return
	 */
	@RequestMapping(value = "buttonList", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getButtonList(
			HttpServletResponse response, Long parentId, Long srId,
			Long sysId) {
		List<Map<String, Object>> list = Lists.newArrayList();
		try {

			List<RolePermission> rolePermissionList = rolePermissionService
					.getRolePermissionListBySrId(srId.intValue(), sysId.intValue());

			List<Permission> queryList = permissionService
					.getPermissionButtonListByParentId(parentId, sysId);
			if ((null != queryList) && (queryList.size() > 0)) {
				for (Permission permission : queryList) {
					Map<String, Object> map = Maps.newHashMap();
					long spId = permission.getSpId();
					map.put("spId", spId);
					map.put("spName", permission.getSpName());
					map.put("sysId", permission.getSysId());
					map.put("spParentId", permission.getSpParentId());
					if (isContainPermission(spId, rolePermissionList)) {
						map.put("checked", true);
					} else {
						map.put("checked", false);
					}
					list.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 判断给定的权限是否已经在role_permission权限表中被授权存在了
	 * 
	 * @param spId
	 * @param list
	 * @return
	 */
	private boolean isContainPermission(long spId, List<RolePermission> list) {
		if ((null != list) && (list.size() > 0)) {
			for (RolePermission rolePermission : list) {
				if (spId == rolePermission.getSpId()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 删除角色操作：
	 * 
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteRoleInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRoleInfo(Long srId) {
		Map<String, Object> map = Maps.newHashMap();

		/**
		 * 在删除角色前，需要判断一下，如果该角色已经有授权记录，则不允许删除
		 */
		RolePermission rolePermission = new RolePermission();
		rolePermission.setSrId(srId);
		List<RolePermission> permissionList = rolePermissionService
				.queryListByEntity(rolePermission);

		if ((null != permissionList) && (permissionList.size() > 0)) {
			map.put("isContainPermission", true);
			map.put("success", false);
		} else {
			map.put("isContainPermission", false);
			Long srIdLong = rolePermission.getSrId();
			// 删除角色信息
			roleService.deleteById(Integer.parseInt("" + srIdLong));
			map.put("success", true);
		}

		return map;
	}

	@Override
	protected void setDefaultValue(HttpServletRequest request,Role t, String operateFlag) {
		
		/**
		 * 获取当前时间
		 */
		if ("update".equals(operateFlag)) {
			/**
			 * 暂时修改：此处暂时设置固定值。待系统完善后根据实际登陆者和时间具体填写
			 */
			t.setModifiedBy("1");
			t.setModifiedDate(new Date());
		} else {
			/**
			 * 暂时修改：此处暂时设置固定值。待系统完善后根据实际登陆者和时间具体填写
			 */
			t.setModifiedBy("1");
			t.setModifiedDate(new Date());
			t.setInsertBy("1");
			t.setInsertDate(new Date());
		}
	}

}
