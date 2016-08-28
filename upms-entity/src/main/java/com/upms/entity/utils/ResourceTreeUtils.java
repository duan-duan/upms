package com.upms.entity.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.model.Tree;
import com.upms.entity.security.Resource;

public class ResourceTreeUtils {
	
	/**
	 * 将List<Resource>转化为Map<Long,Tree>，Tree由Resource构造
	 * @param  resourceList 资源列表
	 * @return Map<Long,Tree> key：资源ID
	 *                        value:资源转化为Tree
	 */
	public static Map<Long,Tree> convertResourceListToTreeMap(List<Resource> resourceList) {
		Map<Long,Tree> result = new HashMap<Long,Tree>();
		for (Resource resource : resourceList) {
			Tree tree = new Tree(resource.getSp_id(),
					resource.getSp_parent_id(), resource.getSp_name(), "false",
					resource.getSys_id(), resource.getRank(),
					resource.getHref());
			tree.setState("open");
			result.put(tree.getId(), tree);
		}
		return result;
	}
	
	
	/**
	 * 将资源列表构造成可供easyui使用的树
	 * @param  resourceList 资源列表
	 * @return List<Tree>   树
	 */
	public static List<Tree> convertResourceListToTreeList(List<Resource> resourceList) {
		Map<Long,Tree> temp = convertResourceListToTreeMap(resourceList);
		// 存储顶级菜单
		List<Tree> topTreeList = new ArrayList<Tree>();
		// 构造子菜单及顶级菜单
		for (Resource resource : resourceList) {

			Tree tree = temp.get(resource.getSp_id());

			if (resource.getSp_parent_id() > 0) {

				Tree parentTree = temp.get(resource.getSp_parent_id());
				
				parentTree = (null==parentTree)?(new Tree()):parentTree;  //解决空指针报错  
				
					if (parentTree.getChildren() == null) {
						parentTree.setChildren(new ArrayList<Tree>());
					}
					parentTree.getChildren().add(tree);
					parentTree.setState("closed");// 默认关闭

			} else {
				topTreeList.add(tree);
			}
		}
		return topTreeList;
	}
	
	/**
	 * 将List<Resource>转化为List<Tree>，并根据treeCheckedIds选中树节点
	 * @param resourceList 资源列表
	 * @param treeCheckedIds 选中的节点列表
	 * @return
	 */
	public static List<Tree> convertResourceListToTreeList(List<Resource> resourceList, List<Long> treeCheckedIds){
		
		Map<Long,Tree> temp = convertResourceListToTreeMap(resourceList);
		// 存储顶级菜单
		List<Tree> topTreeList = new ArrayList<Tree>();
		// 构造子菜单及顶级菜单
		for (Resource resource : resourceList) {

			Tree tree = temp.get(resource.getSp_id());

			if (resource.getSp_parent_id() > 0) {

				Tree parentTree = temp.get(resource.getSp_parent_id());
				
				parentTree = (null==parentTree)?(new Tree()):parentTree;  //解决空指针报错  
				
				if (parentTree.getChildren() == null) {
					parentTree.setChildren(new ArrayList<Tree>());
				}
				parentTree.getChildren().add(tree);
				parentTree.setState("closed");// 默认关闭
			} else {
				topTreeList.add(tree);
			}
			
		}
		
		//设置是否选中
		for(Map.Entry<Long, Tree> entry: temp.entrySet()){
			Tree tree = entry.getValue();
			if ((tree.getChildren() == null || tree.getChildren().size() < 1) && treeCheckedIds.contains(tree.getId())) {
				tree.setChecked(true);
			}
		}
		
		return topTreeList;
	}

}
