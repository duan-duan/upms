/**
 * 
 */
package com.upms.web.controller.security;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.service.BaseService;
import com.common.web.controller.BaseCRUDAction;
import com.upms.entity.security.Permission;
import com.upms.entity.security.Role;
import com.upms.entity.security.SystemModel;
import com.upms.service.api.security.PermissionService;
import com.upms.service.api.security.RoleService;
import com.upms.service.api.security.SystemModelService;
import com.google.common.collect.Maps;

/**
 * 系统模块的Controller
 * @author zhanghaiyang
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/systemModel")
public class SystemModelController extends BaseCRUDAction<SystemModel, Long> {

	@Autowired
	SystemModelService<SystemModel, Long> systemModelService;

	@Autowired
	PermissionService<Permission, Integer> permissionService;

	@Autowired
	RoleService<Role, Integer> roleService;

	@Override
	public BaseService<SystemModel, Long> getBaseService() {
		return systemModelService;
	}

	/**
	 * 删除角色操作：
	 * 
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteSystemModelInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSystemModelInfo(@RequestParam("sysId[]") Long[] sysId) {
		Map<String, Object> map = Maps.newHashMap();

		/**
		 * 在删除角色前，需要判断一下，如果该角色已经有授权记录，则不允许删除
		 */
		Long sysIdInt = sysId[0];
		Role queryRole = new Role();
		queryRole.setSysId(sysIdInt);
		List<Role> roleList = roleService.queryListByEntity(queryRole);

		Permission queryPermission = new Permission();
		queryPermission.setSysId(sysIdInt);
		List<Permission> permissionList = permissionService
				.queryListByEntity(queryPermission);

		if (((null != permissionList) && (permissionList.size() > 0))
				|| ((null != roleList) && (roleList.size() > 0))) {
			map.put("msg", "删除系统信息失败！有角色或权限正在使用该系统信息。");
			map.put("state", false);
		} else {
			// 删除角色信息
			systemModelService.deleteById(sysIdInt);
			map.put("msg", "删除成功");
			map.put("state", true);
		}

		return map;
	}

	@Override
	protected void setDefaultValue(HttpServletRequest request,SystemModel t, String operateFlag) {
		// TODO Auto-generated method stub
		
	}

}
