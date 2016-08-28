/**
 * 
 */
package com.upms.web.controller.security;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.service.BaseService;
import com.common.util.IpUtils;
import com.common.web.controller.BaseCRUDAction;
import com.upms.entity.security.Resource;
import com.upms.entity.security.User;
import com.upms.entity.system.LoginFailLog;
import com.upms.entity.system.LoginSuccessLog;
import com.upms.service.api.security.ResourceService;
import com.upms.service.api.security.UserService;
import com.upms.service.api.system.LoginFailService;
import com.upms.service.api.system.LoginSuccessService;
import com.upms.vo.user.PermissionVo;
import com.upms.web.controller.util.UserSessionProvider;

/**
 * @author zhanghaiyang
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/")
public class LoginController extends BaseCRUDAction<User, Integer> {

	@Autowired
	UserService<User, Integer> userService;

	@Autowired
	ResourceService<Resource, Integer> resourceService;

	@Autowired
	LoginSuccessService<LoginSuccessLog, Integer> loginSuccessService;

	@Autowired
	LoginFailService<LoginFailLog, Integer> loginFailService;

	@Override
	public BaseService<User, Integer> getBaseService() {
		return userService;
	}

	/**
	 * 登录系统的处理
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginInfo(HttpServletRequest request, User user) {

		Map<String, Object> map = null;


		if (user != null) {
			
			String loginAccount = user.getAccount();
			String inputPassword = user.getPassword() ;// 页面上传过来的密码是明文

			/**
			 * 调用函数验证用户信息
			 */
			map = userService.validateLoginUser(user);

			String errorMessage = (String) map.get("ErrorMessage");
			boolean successFlag = (boolean) map.get("success");



			if (successFlag) {// 验证成功后
				User queryCondition = new User();
				queryCondition.setAccount(loginAccount);
				List<User> userList = userService
						.queryListByEntity(queryCondition);
				User adminUser = userList.get(0);

				if (null != adminUser) {
					adminUser.setSysId(user.getSysId());
				}

				saveLogInfo(request, loginAccount, "success", null);// 验证成功后，记录登录成功日志
				saveLoginUserInSession(request, adminUser);// 验证成功后，将该用户信息存入Session中
			} else {// 验证失败后
				String failContent = "username=" + loginAccount + ";password="
						+ inputPassword + ";errorInfo=" + errorMessage;
				saveLogInfo(request, loginAccount, "fail", failContent);// 验证失败后，记录登录失败日志
			}
		}

		return map;
	}

	/*
	 * 记录登录操作的成功日志和失败日志
	 */
	private void saveLogInfo(HttpServletRequest request, String account,
			String logType, String errorMsg) {
		String ip = IpUtils.getIpAddr(request);
		if ("success".equals(logType)) {
			LoginSuccessLog loginSuccessLog = new LoginSuccessLog();
			loginSuccessLog.setIp(ip);
			loginSuccessLog.setUsername(account);
			loginSuccessService.createEntity(loginSuccessLog);
		} else {
			LoginFailLog loginFailLog = new LoginFailLog();
			loginFailLog.setIp(ip);
			loginFailLog.setUsername(account);
			loginFailLog.setContent(errorMsg);
			loginFailService.createEntity(loginFailLog);
		}
	}

	/**
	 * 将用户信息存入Session中
	 * 
	 * @param session
	 * @param userName
	 */
	private void saveLoginUserInSession(HttpServletRequest request,
			User adminUser) {
		if (null != adminUser) {

			User user = userService.initLoginUserInfo(adminUser);
			
			// 将用户信息放入Session中
			UserSessionProvider.setUserSerssion(request, user);
		}
	}

	/**
	 * “安全退出”系统
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String quitToLogin(HttpServletRequest request) {
		// 安全退出的时候将Session清空
		UserSessionProvider.invalidateSerssion(request);
		return redirectLoginToUrl();
	}
	
	/**
	 * 查询出登录用户的权限
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "loginPermission", method = RequestMethod.POST)
	@ResponseBody
	public String getLoginUserPermissions(HttpServletRequest request){
		User loginUser = UserSessionProvider.getUserSerssion(request);
		if(null!=loginUser){
			Set<PermissionVo> permList = loginUser.getPermList();
			String permissionStr = "";
			if(null!=permList&&permList.size()>0){
				for(PermissionVo permissionVo : permList){
					String perStr = permissionVo.getPermission();
					    if((null==perStr)||("".equals(perStr))){
					    	continue;
					    }
						permissionStr = permissionStr + "," + permissionVo.getPermission(); 
				}
				return permissionStr;
			}
		}
		
		
		return null;
	}

	@Override
	protected void setDefaultValue(HttpServletRequest request,User arg0, String arg1) {
	}

}
