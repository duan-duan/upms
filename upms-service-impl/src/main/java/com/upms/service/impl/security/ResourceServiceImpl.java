package com.upms.service.impl.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;
import com.common.model.Tree;
import com.common.service.impl.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.upms.dao.security.ResourceMapper;
import com.upms.entity.security.Resource;
import com.upms.entity.security.SystemModel;
import com.upms.entity.utils.ResourceTreeUtils;
import com.upms.service.api.security.ResourceService;
import com.upms.service.api.security.SystemModelService;


/**
 * 
 * @author guoqiushi
 *
 */
@Service
public class ResourceServiceImpl<T extends BaseEntity, ID extends Serializable>
		extends BaseServiceImpl<T, ID> implements ResourceService<T, ID>{

	@Autowired
	ResourceMapper<T, ID> resourceMapper;
	
	@Autowired
	SystemModelService<SystemModel, Integer> systemModelService;

	@Override
	public BaseMapper<T, ID> getMapper() {
		return resourceMapper;
	}

	@Override
	public List<Tree> getList() {
		List<Tree> rootList = resourceToTree(resourceMapper.getRootList());// 查询所有父节点并转换
		return recursiveTree(rootList);
	}
	
	@Override
	public Resource getNode(long id) {
		return this.resourceMapper.getNode(id);
	}

	@Override
	public int deleteNode(long id) {
		// 查询该节点是否有子节点，如果有则进行级联删除
		List<Resource> children = this.resourceMapper.getChildNodeList(id);
		if (children != null && children.size() > 0) {
			this.resourceMapper.deleteCascade(id);
		}
		return this.resourceMapper.deleteNode(id);
	}

	@Override
	public int addNode(Resource resource) {
		return this.resourceMapper.addNode(resource);
	}

	@Override
	public int updateNode(Resource resource) {
		return this.resourceMapper.updateNode(resource);
	}

	@Override
	public List<Tree> getChildList(long pid) {
		return resourceToTree(this.resourceMapper.getChildNodeList(pid));// 查询某父节点的所有子节点
	}
	
	/**
	 * 根据pid获取所有子节点按钮节点列表
	 * @param pid
	 * @return
	 */
	@Override
	public List<Tree> getChildButtonList(long pid){
		return resourceToTree(this.resourceMapper.getChildButtonNodeList(pid));// 查询某父节点的所有子节点
	}

	@Override
	public List<Tree> getSystemResourceList(long sys_id) {
		return recursiveTree(resourceToTree(resourceMapper.getSystemRootList(sys_id)));
	}

	@Override
	public List<Tree> getSystemTreeList() {
		return systemModelToTree(systemModelService.queryList());
	}

	@Override
	public List<Resource> getButtonListForMenu(long pid) {
		return this.resourceMapper.getButtonListForMenu(pid);
	}

	@Override
	public List<Tree> getSystemResourceChildList(long sys_id, long pid) {
		return recursiveTree(resourceToTree(resourceMapper.getSystemResourceChildList(sys_id, pid)));
	}
	/**
	 * 递归遍历所有根节点，生成树状结构
	 * 
	 * @param roots
	 * @return
	 */
	private List<Tree> recursiveTree(List<Tree> roots) {
		for (Tree tree : roots) {
			List<Tree> childList = resourceToTree(resourceMapper.getChildNodeList(tree.getId())); // 查询根节点的子节点

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
	private List<Tree> resourceToTree(List<Resource> resources) {

		List<Tree> trees = Lists.newArrayList();

		for (Resource resource : resources) {
			Tree tree = new Tree(resource.getSp_id(),resource.getSp_parent_id(), resource.getSp_name(), "false",
					             resource.getSys_id(),resource.getRank(),resource.getHref());
			trees.add(tree);
		}
		return trees;
	}

	/**
	 * 将系统和资源整合成树状列表
	 * 
	 * @param systemModels
	 * @return
	 */
	private List<Tree> systemModelToTree(List<SystemModel> systemModels) {
		List<Tree> trees = Lists.newArrayList();
		Tree tree = null;
		for (SystemModel systemModel : systemModels) {
			List<Resource> roots = resourceMapper.getSystemRootList(systemModel
					.getId());// 找到该系统下资源的根节点
			tree = new Tree(systemModel.getName(), "open",
					recursiveTree(resourceToTree(roots)), "true",
					systemModel.getId());// 转换成easyui-tree 需要的json格式
			tree.setId(systemModel.getId());
			trees.add(tree);
		}
		return trees;
	}
	
	
	
	
	
	//----------------------------------------------------登录后显示菜单相关-------------------------------------------------------
	/**
	 * 
	 * 登录用户进入系统后看到左侧菜单权限控制显示的实现
	 * 
	 */
	
	public List<Tree> getListForUser1(String account,long sysId){
		List<Resource> selectedList = resourceMapper.getSelectedList(account);
		List<Resource> userRootList = new ArrayList<Resource>();
		String saveSecondLevelSpIds = "";
		
		/**
		 * 获得从根节点开始的第二级节点，这些节点都是这个用户有权限的
		 */
		if(null!=selectedList&&selectedList.size()>0){
			for(Resource resource:selectedList){
				Resource getSecondLevelRootResource = getSecondLevelRootForUser(resource.getSp_id(),selectedList);
				if(null!=getSecondLevelRootResource){
					
					if(!saveSecondLevelSpIds.contains(getSecondLevelRootResource.getSp_id()+"")){
						userRootList.add(getSecondLevelRootResource);
					}
					
					saveSecondLevelSpIds = saveSecondLevelSpIds+","+getSecondLevelRootResource.getSp_id();
				}
			}			
		}
		
		String allPermissionNodeIds = "";
		/**
		 * 从被选中的节点开始，一直往上找，找到所有的可能的连接节点
		 */
		if(null!=selectedList&&selectedList.size()>0){
			for(Resource resource:selectedList){
				allPermissionNodeIds = getAllRootNodesStrForUser(resource.getSp_id(),allPermissionNodeIds,selectedList);
				allPermissionNodeIds = allPermissionNodeIds+",";
			}			
		}
		
		List<Tree> rootList = resourceToLoginUserTree(userRootList);// 查询所有父节点并转换
		List<Tree> trees = recursiveLoginUserTree(rootList,allPermissionNodeIds);
		
		/**
		 * 将得到树形每个节点过滤一下，去掉不是该系统的节点
		 */
		List<Tree> permissionTrees = Lists.newArrayList();
		if((null!=trees)&&(trees.size()>0)){
			for(Tree tree: trees){
				if(sysId==tree.getSys_id()){
					permissionTrees.add(tree);
				}
			}
		}
		return permissionTrees;
	}
	
	public List<Tree> getListForUser(String account,long sysId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("account", account);
		map.put("sysId", new Long(sysId));
		List<Resource> authorizedList = resourceMapper.getAuthorizedList(map);
		//存储顶级菜单
		List<Tree> topTreeList = ResourceTreeUtils.convertResourceListToTreeList(authorizedList);
		
		return topTreeList;
	}
	
	/**
	 * 递归调用，找到二级菜单节点（父亲节点为0的下一级菜单）
	 * @param resultList
	 * @param spId
	 * @return
	 */
	private Resource getSecondLevelRootForUser(long spId,List<Resource> list){
		Resource result = null;
		if(spId>=0){
			Resource queryResource = 	getResourceBySpId(spId,list);
		    if((null!=queryResource)){
		    	if(queryResource.getSp_parent_id()!=0){//没有找到父节点为根节点的时候，继续往上找
		    		    result = getSecondLevelRootForUser(queryResource.getSp_parent_id(),list);
		    	}else{
		    			result = queryResource;
		    	}
		    }
		}
		
		return result;
	}
	
	/**
	 * 递归调用，从某个几点一直往上追溯，找到所有它连接的节点
	 * @param str
	 * @param spId
	 * @return
	 */
	private String getAllRootNodesStrForUser(long spId,String str,List<Resource> list){
		if(spId>=0){
			Resource queryResource =getResourceBySpId(spId,list);;
		    if((null!=queryResource)){
		    	
		    	if(queryResource.getSp_parent_id()!=0){
		    		    str = str +","+queryResource.getSp_id();
		    		    str = getAllRootNodesStrForUser(queryResource.getSp_parent_id(),str,list);
		    	}
		    }
		}
		
		return str;
	}
	
	
	private Resource getResourceBySpId(long spId,List<Resource> list){
		
		if((null!=list)&&(list.size()>0)){
			for(Resource res :list){
				if(spId==res.getSp_id()){
					return res;
				}
			}
		}
		
		return null;
	}
	
	
	/**
	 * 将资源列表转化成页面可展现成easyui-treegrid列表
	 * 
	 * @param resources
	 * @return
	 */
	private List<Tree> resourceToLoginUserTree(List<Resource> resources) {

		List<Tree> trees = Lists.newArrayList();

		for (Resource resource : resources) {
			Tree tree = new Tree(resource.getSp_id(),resource.getSp_parent_id(), resource.getSp_name(), "false",
					             resource.getSys_id(),resource.getRank(),resource.getHref());
			trees.add(tree);
		}
		return trees;
	}
	
	/**
	 * 递归遍历所有根节点，生成树状结构
	 * 
	 * @param roots
	 * @return
	 */
	private List<Tree> recursiveLoginUserTree(List<Tree> roots,String permissionNodesStr) {
		for (Tree tree : roots) {
			List<Resource> treeCildrenNodeList = resourceMapper.getChildNodeList(tree.getId());
			
			List<Tree> childList = resourceToLoginUserTree(filterHasPermissionNodeList(permissionNodesStr,treeCildrenNodeList)); // 查询根节点的子节点

			if (childList != null && childList.size() > 0) {
				tree.setChildren(childList);
				recursiveLoginUserTree(childList,permissionNodesStr);
			} else {
				tree.setState("open");
			}
		}
		return roots;
	}
	
	
	/**
	 * 只有页面被选中的节点以及他们到根节点的路径上的所有节点才有权限显示出来，其他的被过滤
	 * @param permissionNodesStr
	 * @param treeCildrenNodeList
	 * @return
	 */
	private List<Resource> filterHasPermissionNodeList(String permissionNodesStr,List<Resource> treeCildrenNodeList){
		List<Resource> resultList = Lists.newArrayList();
		if((null!=treeCildrenNodeList)&&(treeCildrenNodeList.size()>0)){
			for(Resource resource : treeCildrenNodeList){
				if((null!=resource)&&(null!=permissionNodesStr)&&(permissionNodesStr.contains(resource.getSp_id()+""))){
					resultList.add(resource);
				}
			}
		}
		return resultList;
	}

}
