/**
 * 
 */
package com.upms.vo.user;

import com.common.entity.BaseSerializable;

/**
 * @author zhanghaiyang
 * 
 */
public class PermissionVo implements BaseSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7939792532221719015L;

	/**
	 * URL
	 */
	private String href;

	/**
	 * 授权
	 */
	private String permission;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public PermissionVo() {
		super();
	}

	public PermissionVo(String href, String permission) {
		super();
		this.href = href;
		this.permission = permission;
	}

}
