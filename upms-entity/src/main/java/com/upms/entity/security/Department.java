/**
 * 
 */
package com.upms.entity.security;

import com.common.entity.BaseEntity;

/**
 * @author zhanghaiyang
 *
 */
public class Department extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long departmentId;
	
	private String departmentName;
	
	private String departmentCode;
	
	private Long departmentParentId;
	
	private String departmentDescribe;
	
	private Long departmentRank;
	
	
	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public Long getDepartmentParentId() {
		return departmentParentId;
	}

	public void setDepartmentParentId(Long departmentParentId) {
		this.departmentParentId = departmentParentId;
	}

	public String getDepartmentDescribe() {
		return departmentDescribe;
	}

	public void setDepartmentDescribe(String departmentDescribe) {
		this.departmentDescribe = departmentDescribe;
	}

	public Long getDepartmentRank() {
		return departmentRank;
	}

	public void setDepartmentRank(Long departmentRank) {
		this.departmentRank = departmentRank;
	}
}
