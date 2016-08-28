/**
 * 
 */
package com.upms.entity.security;

import com.common.entity.BaseEntity;
import com.common.util.JsonUtil;


/**
 * @author zhanghaiyang
 *
 */
public class SystemModel extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	private String description;
	/**
	 * 需要排序字段 暂时只能对单字段排序，以后可以改为对多列排序，此字段为数据库中的字段，非对象.
	 */
	private String orderBy = "id";
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return JsonUtil.objectToJSON(this);
	}

}
