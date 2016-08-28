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
import com.upms.dao.security.PermissionMapper;
import com.upms.dao.security.ResourceMapper;
import com.upms.entity.security.Permission;
import com.upms.entity.security.Resource;
import com.upms.entity.security.SystemModel;
import com.upms.service.api.security.PermissionService;
import com.upms.service.api.security.SystemModelService;

/**
 * @author zhanghaiyang
 * 权限Service实现类
 */
@Service
public class PermissionServiceImpl <T extends BaseEntity, ID extends Serializable> extends BaseServiceImpl<T, ID> implements PermissionService<T, ID>{

	@Autowired
	private PermissionMapper<T, ID> permissionMapper;
	
	@Autowired
	private SystemModelService<SystemModel, Integer> systemModelService;
	
	
	@Autowired
	ResourceMapper<Resource,Integer> resourceMapper;
	
	@Override
	public BaseMapper<T, ID> getMapper() {
		return permissionMapper;
	}
	
	/**
	 * 获取所有权限树形信息，不包含默认勾选
	 */
	@Override
	public List<Tree> getPermissionTreeList(long sysId) {
		return systemModelToTree(this.systemModelService.queryList(),null,sysId);
	}
	
	
	
	/**
	 * 获取所有权限树形信息，包含默认勾选
	 */
	@Override
	public List<Tree> getPermissionTreeListContainsDefault(List<String> defaultSpIdStrList,long sysId) {
		return systemModelToTree(this.systemModelService.queryList(),defaultSpIdStrList,sysId);
	}
	
	

	private List<Tree> systemModelToTree(List<SystemModel> list,List<String> defaultSpIdStrList,long getSysId){
		List<Tree> trees = Lists.newArrayList();
		    SystemModel systemModel = (SystemModel) systemModelService.queryById(new Integer(getSysId+""));
		    if(null!=systemModel){
		    	List<Resource> roots = this.resourceMapper.getSystemRootList(getSysId);//找到该系统下资源的根节点 
				Tree tree = new Tree();//转换成easyui-tree 需要的json格式
				long sysId = systemModel.getId();
				tree.setId(sysId);
				tree.setText(systemModel.getName());
				tree.setState("open");
				tree.setChildren(recursiveTree(this.resourceToTree(roots,defaultSpIdStrList),defaultSpIdStrList));
				
				trees.add(tree);
		    }
		return trees;
	}
	
     private List<Tree> recursiveTree(List<Tree> roots,List<String> defaultSpIdStrList){
		
		for(Tree tree:roots){
			List<Tree> childList = this.resourceToTree(this.resourceMapper.getChildNodeList(tree.getId()),defaultSpIdStrList); //查询根节点的子节点
			if(childList!=null && childList.size()>0){
				tree.setChildren(childList);
				recursiveTree(childList,defaultSpIdStrList);
			}else{
				tree.setState("open");
			}
		}
		return roots;
	}
     
     private List<Tree> resourceToTree(List<Resource> resources,List<String> defaultSpIdStrList){
 		
 		List<Tree> trees = Lists.newArrayList();
 		
 		for(Resource resource:resources){
 			Tree tree = new Tree();
 			long spId = resource.getSp_id();
 			tree.setId(spId);
 			tree.setText(resource.getSp_name());
 			tree.setPid(resource.getSp_parent_id());
 			
			/*
			 * CheckBox默认勾选：   如果传入了默认值，则勾选上
			 */
			if((null!=defaultSpIdStrList)&&(defaultSpIdStrList.size()>0)&&(defaultSpIdStrList.contains(spId+""))){
				tree.setChecked(true);
			}
			
 			trees.add(tree);
 		}
 		return trees;
 	}
     
     /**
      * 通过父节点ID获取权限按钮
      */
     @Override
     public List<Permission> getPermissionButtonListByParentId(Long parentId,Long sysId){
    	   return permissionMapper.getPermissionButtonListByParentId(parentId,sysId);
     }
     
     /**
      * 获取某用户所有权限列表
      */
     @Override
     public List<Permission> getAllPermissionForUser(String account,Long sysId){
    	 return permissionMapper.getAllPermissionByUserAccount(account, sysId);
     }


}
