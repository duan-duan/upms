/**
 * 
 */
package com.upms.entity.security;

import com.common.entity.BaseEntity;

/**
 * @author ouyang
 * 
 */
public class UserRole extends BaseEntity {
	
	private static final long serialVersionUID = 3447015012581203055L;

	/**
	 * 用户ID
	 */
	private Long suId;
	/**
	 * 角色ID
	 */
	private Long srId;
	
	
	public Long getSuId() {
		return suId;
	}

	public void setSuId(Long suId) {
		this.suId = suId;
	}

	public Long getSrId() {
		return srId;
	}

	public void setSrId(Long srId) {
		this.srId = srId;
	}
	
}
