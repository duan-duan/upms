package com.upms.service.impl.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;
import com.common.service.impl.BaseServiceImpl;
import com.upms.dao.security.UserRoleMapper;
import com.upms.entity.security.Role;
import com.upms.service.api.security.UserRoleService;

@Service
public class UserRoleServiceImpl<T extends BaseEntity, ID extends Serializable> extends BaseServiceImpl<T, ID> implements UserRoleService<T, ID> {

	@Autowired
	UserRoleMapper<T, ID> userRoleMapper;
	
	@Autowired
	private ReloadableResourceBundleMessageSource reloadSource;
	
	private  Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 根据用户ID查找用户的角色
	 */
	@Override
	public List<Role> getRolesByUserId(Map<String, String> userinfo) {
		return userRoleMapper.getRolesByUserId(userinfo);
	}

	/**
	 * 根据用户ID查找用户未有的角色
	 */
	@Override
	public List<Role> getRolesByNotUserId(Map<String, String> userinfo) {
		return userRoleMapper.getRolesByNotUserId(userinfo);

	}

	/**
	 * 查询用户已有角色和未有角色
	 */
	public Map<String, List<Role>> selectRoleManager(String userId,String currentUserAccount) {
		
		String[] accountArray = {};
		boolean restrictFlag = false;
		
		String restrictAccount = null;
		String restrictRole = null;
		try {
			/**从配置文件中获取被限制的账户*/
			 restrictAccount = reloadSource.getMessage("restrict.account",null,Locale.CHINA);
			/**从配置文件中获取被限制的角色*/
			 restrictRole = reloadSource.getMessage("restrict.role",null,Locale.CHINA);
		} catch (Exception e) {
			logger.error("[从配置文件中获取被限制账户和角色出现异常,异常信息:{}]",e.getMessage());
		}
		
		/**判断当前登录账户是否要被限制*/
		if(restrictAccount != null && restrictAccount != "" && restrictAccount.length() > 0){
			accountArray = restrictAccount.split(",");
			for(String account : accountArray){
				if(account.equalsIgnoreCase(currentUserAccount)){
					restrictFlag = true;
					break;
				}
			}
		}
		
		Map<String, String> userinfo = new HashMap<String, String>();
		userinfo.put("userId", userId);
		List<Role> ownRoles = userRoleMapper.getRolesByUserId(userinfo);
		List<Role> newRoles = userRoleMapper.getRolesByNotUserId(userinfo);
		
		/**被限制后的已有角色列表*/
		List<Role> restrictownRoles = new ArrayList<Role>();
		/**被限制后的可选角色列表*/
		List<Role> restrictnewRoles = new ArrayList<Role>();
		
		/**创建被限制的角色列表*/
		createRestrictRoleList(restrictFlag, ownRoles, newRoles, restrictownRoles, restrictnewRoles,restrictRole);
		
		Map<String, List<Role>> roles = new HashMap<String, List<Role>>();
		
		/**当前登录用户被限制，并且被限制的角色列表不为空就展示被限制的角色列表*/
		if(restrictFlag == true){
			roles.put("newRoles", restrictnewRoles);
			roles.put("ownRoles", restrictownRoles);
		}else{
			roles.put("newRoles", newRoles);
			roles.put("ownRoles", ownRoles);
		}
		
		return roles;
	}

	/**
	 * 修改用户角色
	 */
	public void saveOrUpdateUserRoles(String userId, String roleIds) {
		Map<String, Object> userRoles = new HashMap<String, Object>();
		userRoles.put("userId", Integer.parseInt(userId));
		// 删除已有角色
		userRoleMapper.deleteRoleByUser(userRoles);
		String[] ids = null;
		if (roleIds != null && roleIds.length() > 0) {
			ids = roleIds.split(",");
		}
		if(ids !=null && ids.length>0){
			userRoles.put("roleIds", ids);
			userRoleMapper.insertRolesForUser(userRoles);
		}
	}
	
	private void createRestrictRoleList(boolean restrictFlag, List<Role> ownRoles, List<Role> newRoles,
			List<Role> restrictownRoles, List<Role> restrictnewRoles, String restrictRole) {
		
		String[] restrictRoleArray = {};
		
		if(restrictRole != null && restrictRole != "" && restrictRole.length() > 0){
			restrictRoleArray = restrictRole.split(",");
		}
		
		/**只放入被限制的角色*/
		if(restrictFlag == true){
			if(ownRoles != null && ownRoles.size() > 0 && restrictRoleArray.length > 0){
				for(Role role : ownRoles){
					long id = role.getId(); 
					for(String rRole : restrictRoleArray){
						long rId = Long.parseLong(rRole); 
						if(id == rId){
							restrictownRoles.add(role);
						}
					}
				}
			}
			
			if(newRoles != null && newRoles.size() > 0 && restrictRoleArray.length > 0){
				for(Role role : newRoles){
					long id = role.getId(); 
					for(String rRole : restrictRoleArray){
						long rId = Long.parseLong(rRole); 
						if(id == rId){
							restrictnewRoles.add(role);
						}
					}
				}
			}
		}
	}

	@Override
	public BaseMapper<T, ID> getMapper() {
		return userRoleMapper;
	}

	@Override
	public List<Map<String, Object>> queryMasLocList() {
		return userRoleMapper.queryMasLocList();
	}

	@Override
	public List<Map<String, Object>> queryLsPList() {
		return userRoleMapper.queryLsPList();
	}

}
