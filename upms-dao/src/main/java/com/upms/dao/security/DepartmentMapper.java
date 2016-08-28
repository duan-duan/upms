/**
 * 
 */
package com.upms.dao.security;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;
import com.upms.entity.security.Department;

/**
 * @author zhanghaiyang
 *
 */
public interface DepartmentMapper <T extends BaseEntity, ID extends Serializable>
extends BaseMapper<T, ID>  {
	
	List<Department> getRootList();
	
	List<Department> getChildNodeList(@Param("pid") long pid);
	
	Department getNode(@Param("ID") long id);
	
	List<Department> getNodeListByCondition(@Param("id") long id,@Param("lengthValue") int lengthValue);
	
}
