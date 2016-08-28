/**
 * 
 */
package com.upms.entity.security;

import java.util.ArrayList;
import java.util.List;

import com.common.entity.BaseEntity;

/**
 * @author zhanghaiyang
 *
 */
public class Permission extends BaseEntity{
	
	private static final long serialVersionUID = -7576992198390260185L;

	private Long spId;
	
	private String spName;
	
	private Long spParentId;
	
	private String spDesc;
	
	private String href;
	
	private String target;
	
	private String icon;
	
	private String spVisible;
	
	private String spType;
	
	private String permission;
	
	private String version;
	
	private Long sysId;
	
	private List<Permission> childrenList;
	
	/**
	 * 需要排序字段 暂时只能对单字段排序，以后可以改为对多列排序，此字段为数据库中的字段，非对象.
	 */
	private String orderBy = "sp_id";
	

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public Long getSpId() {
		return spId;
	}

	public void setSpId(Long spId) {
		this.spId = spId;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public Long getSpParentId() {
		return spParentId;
	}

	public void setSpParentId(Long spParentId) {
		this.spParentId = spParentId;
	}

	public String getSpDesc() {
		return spDesc;
	}

	public void setSpDesc(String spDesc) {
		this.spDesc = spDesc;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSpVisible() {
		return spVisible;
	}

	public void setSpVisible(String spVisible) {
		this.spVisible = spVisible;
	}

	public String getSpType() {
		return spType;
	}

	public void setSpType(String spType) {
		this.spType = spType;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<Permission> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<Permission> childrenList) {
		this.childrenList = childrenList;
	}
	
    //添加孩子的方法  
    public void addChildList(Permission node){  
       if(this.childrenList == null){  
    	   childrenList= new ArrayList<Permission>();  
    	   childrenList.add(node);  
       }else{  
    	   childrenList.add(node);  
       }  
            
    }  
}
