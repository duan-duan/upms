package com.upms.service.impl.security;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;
import com.common.security.encoder.Md5PwdEncoder;
import com.common.service.impl.BaseServiceImpl;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.upms.dao.security.PermissionMapper;
import com.upms.dao.security.RoleMapper;
import com.upms.dao.security.UserMapper;
import com.upms.entity.security.Permission;
import com.upms.entity.security.User;
import com.upms.service.api.security.UserService;
import com.upms.vo.user.PermissionVo;

import org.springframework.util.CollectionUtils;

@Service
public class UserServiceImpl<T extends BaseEntity, ID extends Serializable>
		extends BaseServiceImpl<T, ID> implements UserService<T, ID> {

	@Autowired
	UserMapper<T, ID> userMapper;
	
	@Autowired
	RoleMapper<T, ID> roleMapper;
	
	@Autowired
	private PermissionMapper<T, ID> permissionMapper;
	
	private static final String SU_ACTIVE_YES = "Y";


	@Override
	public BaseMapper<T, ID> getMapper() {
		return userMapper;
	}
	

	/**
	 * 校验登录
	 */
	@Override
	public Map<String, Object> validateLoginUser(User adminUser) {
		Map<String,Object> resultMap = Maps.newHashMap();
		String code = null;
		String message = null;
		boolean successFlag = false;
	        User queryCondition  =  new User();
	        queryCondition.setAccount(adminUser.getAccount());
	        @SuppressWarnings("unchecked")
			User getUser = (User) userMapper.queryByEntity((T) queryCondition);
	        
	        if (getUser != null) { 
	        	Long enabled = getUser.getEnabled();
	        	String loginPassword = Md5PwdEncoder.encodePassword(adminUser.getPassword(),adminUser.getAccount());
	        	
	        	if(getUser.getPassword().equals(loginPassword)){
					if ((null != enabled) && (enabled == 1)) {
						code = "2000";
		        		message = "OK";
		        		successFlag = true;
		        	}else{
		        		code = "2006";
		        		message = "该用户未激活，不允许登录!";
		        	}
	        	}else{
	        		code = "2002";
	        		message = "登录用户密码错误!";
	        	}
	        } else {  
	        	code = "2001";
        		message = "该用户在系统中不存在!请注册新用户!";
	        }  
	        
	        resultMap.put("ErrorCode", code);
	        resultMap.put("ErrorMessage", message);
	        resultMap.put("success", successFlag);
			
		return resultMap;
	}
	

	/**
	 * 查出登录用户的权限，初始化到该用户对象中
	 */
	@Override
	public User initLoginUserInfo(User adminUser) {
		

		if(null!=adminUser){
			String account = adminUser.getAccount();
			
			/**
			 * 初始化所有的权限permList
			 */
			List<Permission> permissionList = permissionMapper.getAllPermissionByUserAccount(account, adminUser.getSysId());
			Set<PermissionVo> permissionSet = Sets.newHashSet();
			if(null!=permissionList&&permissionList.size()>0){
				for(Permission permission : permissionList){
					String permissionStr = permission.getPermission();
					String href = permission.getHref();
					PermissionVo perm = new PermissionVo(href,permissionStr);
					permissionSet.add(perm);
				}
			}
			adminUser.setPermList(permissionSet);
			
		}
		
		return adminUser;
	}



	@Override
	public String validateUserPermission(long suId) {
		return userMapper.validateUserPermission(suId);
	}
	
	@Override
	@Transactional
	public int createUser(User user) {
		@SuppressWarnings("unchecked")
		int createEntity = this.userMapper.createEntity((T) user);
		if (!user.getMasLocList().isEmpty())
			userMapper.createMasEntity(user);
		return createEntity;
	}


	/* (non-Javadoc)
	 * @see com.upms.service.api.security.UserService#queryMasLocs(java.lang.String)
	 */
	@Override
	public List<String> queryMasLocs(String suAccount) {
		return userMapper.queryMasLocs(suAccount);
	}

	@Override
	public List<User> getUsersByMasloc(String masloc) {
		return userMapper.queryUsersByMasloc(masloc);
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public int updateUser(User user) {
		Map<String, Object> params = Maps.newHashMap();
		Long ids[] = new Long[]{user.getId()};
		params.put("ids", ids);
		int updateEntity = this.updateEntity((T) user);
		/**
		 *
		 mysql版本升经5.6以后不充许同一张表在一个事务未完成的时候发生第二次操作
		userMapper.deleteByAccount(params);
		if (!user.getMasLocList().isEmpty())
			userMapper.createMasEntity(user);
		 */
		if (user.getMasLocList().isEmpty()){
			userMapper.deleteByAccount(params);
		}else{
			List<String> maslocs = userMapper.queryMasLocs(user.getAccount());
			if(CollectionUtils.isEmpty(maslocs)){
				userMapper.createMasEntity(user);
			}else {
				userMapper.updateMasEntity(user);
			}
		}
		return updateEntity;
	}


	/* (non-Javadoc)
	 * 更改账户激活
	 */
	@Override
	public int updateById(Integer ids[]) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("ids", ids);
		return userMapper.updateById(params);
	}

}
