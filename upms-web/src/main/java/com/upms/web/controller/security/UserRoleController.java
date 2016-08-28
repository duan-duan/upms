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
import com.upms.entity.security.Role;
import com.upms.entity.security.User;
import com.upms.entity.security.UserRole;
import com.upms.service.api.security.UserRoleService;
import com.google.common.collect.Maps;
import com.upms.web.controller.util.UserSessionProvider;

/**
 * 用户角色Controller
 * @author zhanghaiyang
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/userRole")
public class UserRoleController extends BaseCRUDAction<UserRole, Integer> {

	@Autowired
	UserRoleService<UserRole, Integer> userRoleService;
	
	@RequestMapping(value = "queryMasLocList", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> queryMasLocList() {
		return userRoleService.queryMasLocList();
	}

	/**
	 * 取承运商数据
	 * @return
	 */
	@RequestMapping(value = "queryLspList", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> queryLspList() {
		return userRoleService.queryLsPList();
	}
	/**
	 * 角色管理
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	@RequestMapping(value = "roleManager", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> roleManager(
			@RequestParam("userId") String userId,
			@RequestParam("roleIds") String roleIds) {
		Map<String, String> msg = Maps.newHashMap();
		try {
			userRoleService.saveOrUpdateUserRoles(userId, roleIds);
			msg.put("msg", "修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			msg.put("msg", "修改失败！");
		}
		return msg;
	}

	/**
	 * 获取某个用户的所有角色
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "roles", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, List<Role>> roles(@RequestParam("userId") String userId,HttpServletRequest request) {
		
		User currentUser = UserSessionProvider.getUserSerssion(request);
		
		if (userId != null && userId.length() > 0) {
			Map<String, List<Role>> roles = userRoleService
					.selectRoleManager(userId,currentUser.getAccount());
			return roles;
		}
		return null;
	}

	@Override
	public BaseService<UserRole, Integer> getBaseService() {
		return userRoleService;
	}

	@Override
	protected void setDefaultValue(HttpServletRequest request,UserRole t, String operateFlag) {
	}
}
