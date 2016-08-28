/**
 * 
 */
package com.upms.service.impl.security;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;
import com.common.model.Tree;
import com.common.service.impl.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.upms.dao.security.DepartmentMapper;
import com.upms.entity.security.Department;
import com.upms.service.api.security.DepartmentService;

/**
 * @author zhanghaiyang
 * 部门Service
 */
@Service
public class DepartmentServiceImpl <T extends BaseEntity, ID extends Serializable>
extends BaseServiceImpl<T, ID> implements DepartmentService<T, ID> {

	@Autowired
	DepartmentMapper<T, ID> departmentMapper;
	@Override
	public BaseMapper<T, ID> getMapper() {
		return departmentMapper;
	}
	
	/**
	 * 加载树形菜单列表
	 * @return
	 */
	@Override
	public List<Tree> getList() {
		List<Tree> rootList = resourceToTree(departmentMapper.getRootList());// 查询所有父节点并转换
		return recursiveTree(rootList);
	}
	
	/**
	 * 树形结构的孩子节点
	 * @param id
	 * @return
	 */
	@Override
	public List<Tree> getChildrenList(long id) {
		return resourceToTree(departmentMapper.getChildNodeList(id));// 查询某父节点的所有子节点
	}
	
	/**
	 * 获取树的某个节点
	 * @param id
	 * @return
	 */
	@Override
	public Department getNodeById(long id) {
		return departmentMapper.getNode(id);
	}
	
	
	/**
	 * 递归遍历所有根节点，生成树状结构
	 * 
	 * @param roots
	 * @return
	 */
	private List<Tree> recursiveTree(List<Tree> roots) {
		for (Tree tree : roots) {
			List<Tree> childList = resourceToTree(departmentMapper.getChildNodeList(tree.getId())); // 查询根节点的子节点

			if (childList != null && childList.size() > 0) {
				tree.setChildren(childList);
				recursiveTree(childList);
			} else {
				tree.setState("open");
			}
		}
		return roots;
	}

	/**
	 * 将资源列表转化成页面可展现成easyui-treegrid列表
	 * 
	 * @param resources
	 * @return
	 */
	private List<Tree> resourceToTree(List<Department> departmentList) {

		List<Tree> trees = Lists.newArrayList();

		for (Department department : departmentList) {
			Tree tree = new Tree();
			tree.setId(department.getDepartmentId());
			tree.setCode(department.getDepartmentCode());
			tree.setDescribe(department.getDepartmentDescribe());
			tree.setPid(department.getDepartmentParentId());
			tree.setText(department.getDepartmentName());
			tree.setRank(Integer.parseInt(department.getDepartmentRank()+""));
			tree.setIsSystem("false");
			
			trees.add(tree);
		}
		return trees;
	}

	/**
	 * 传入要插入子节点ID的父节点ID值，计算出将要插入的子节点的ID
	 */
	@Override
	public long caculateDepartmentId(long id) {
		if(id==1){
			List<Department> queryList = departmentMapper.getNodeListByCondition(1, 2);
			if((null!=queryList)&&(queryList.size()>0)){
				Department dep = queryList.get(queryList.size()-1);
				return dep.getDepartmentId()+1;
			}else{
				return 11;
			}
		}else{
			List<Department> queryList = departmentMapper.getNodeListByCondition(id, ((""+id).length()+2));
			if((null!=queryList)&&(queryList.size()>0)){
				Department dep = queryList.get(queryList.size()-1);
				return dep.getDepartmentId()+1;
			}else{
				String idStr = ""+id+"01";
				return Long.parseLong(idStr);
			}
		}
	}
	
}
