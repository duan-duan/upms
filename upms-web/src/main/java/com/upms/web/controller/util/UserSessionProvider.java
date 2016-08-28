/**
 * 
 */
package com.upms.web.controller.util;

import javax.servlet.http.HttpServletRequest;

import com.common.web.session.HttpSessionProvider;
import com.upms.entity.security.User;

/**
 * @author huanggaoren 用户Session提供
 */
public class UserSessionProvider extends HttpSessionProvider {

	/**
	 * 用户session
	 */
	private static final String SESSION_USER = "session_user";

	/**
	 * 验证码
	 */
	private static final String SESSION_VALIDATECODE = "session_validatecode";

	/**
	 * 设置用户信息 到session
	 * 
	 * @param request
	 * @param user
	 */
	public static void setUserSerssion(HttpServletRequest request, User user) {
		setAttribute(request, SESSION_USER, user);
	}

	/**
	 * 从session中获取用户信息
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static User getUserSerssion(HttpServletRequest request) {
		return (User) getAttribute(request, SESSION_USER);
	}

	/**
	 * 从session中获取用户信息
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static Long getUserSerssionId(HttpServletRequest request) {
		User user = getUserSerssion(request);
		if (user != null) {
			return user.getId();
		}
		return null;
	}

	/**
	 * 从session中获取用户信息
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static void removeUser(HttpServletRequest request) {
		removeAttribute(request, SESSION_USER);
	}

	/**
	 * 设置验证码 到session
	 * 
	 * @param request
	 * @param user
	 */
	public static void setValidateCode(HttpServletRequest request,
			String validateCode) {
		request.getSession(true).setAttribute(SESSION_VALIDATECODE,
				validateCode);
	}

	/**
	 * 从session中获取验证码
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static String getValidateCode(HttpServletRequest request) {
		return (String) request.getSession(true).getAttribute(
				SESSION_VALIDATECODE);
	}

	/**
	 * 从session中获删除验证码
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static void removeValidateCode(HttpServletRequest request) {
		removeAttribute(request, SESSION_VALIDATECODE);
	}

}
