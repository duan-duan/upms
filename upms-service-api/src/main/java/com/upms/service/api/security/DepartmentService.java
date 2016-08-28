/**
 * 
 */
package com.upms.service.api.security;

import java.io.Serializable;
import java.util.List;

import com.common.entity.BaseEntity;
import com.common.model.Tree;
import com.common.service.BaseService;
import com.upms.entity.security.Department;

/**
 * @author zhanghaiyang
 * 部门模块服务
 */
public interface DepartmentService <T extends BaseEntity, ID extends Serializable> extends BaseService<T, ID>{

	/**
	 * 加载树形菜单列表
	 * @return
	 */
	public List<Tree> getList();
	
	/**
	 * 树形结构的孩子节点
	 * @param id
	 * @return
	 */
	public List<Tree> getChildrenList(long id);
	
	/**
	 * 获取树的某个节点
	 * @param id
	 * @return
	 */
	public Department getNodeById(long id);
	
	/**
	 * 传入要插入子节点ID的父节点ID值，计算出将要插入的子节点的ID
	 * @param id
	 * @return
	 */
	public long caculateDepartmentId(long id);
}
