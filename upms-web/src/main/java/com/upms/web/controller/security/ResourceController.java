package com.upms.web.controller.security;                     
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.model.Tree;
import com.common.service.BaseService;
import com.common.util.BaseResponse;
import com.common.web.controller.BaseCRUDAction;
import com.upms.entity.security.Resource;
import com.upms.service.api.security.ResourceService;
import com.google.common.collect.Maps;

/**
 * 菜单资源管理模块
 * @author guoqiushi
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/resource")
public class ResourceController extends BaseCRUDAction<Resource, Integer>{

	@Autowired
	ResourceService<Resource, Integer> resourceService;

	@Override
	public BaseService<Resource, Integer> getBaseService() {
		return resourceService;
	}
	
	
	/**
	 * 加载所有节点
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "tree_json", method = RequestMethod.GET)
	@ResponseBody
	public List<Tree> getList(HttpServletRequest request) {
		return resourceService.getList();
	}
	
	
	/**
	 * 根据父节点获取子树节点
	 * @param request
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "get_child_node", method = RequestMethod.GET)
	@ResponseBody
	public List<Tree> getChildList(HttpServletRequest request,int pid) {
		return resourceService.getChildList(pid);
	}
	
	/**
	 * 根据父节点获取子树节点--包括按钮节点
	 * @param request
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "get_child_button_node", method = RequestMethod.GET)
	@ResponseBody
	public List<Tree> getChildButtonList(HttpServletRequest request,int pid) {
		return resourceService.getChildButtonList(pid);
	}
	
	
	/**
	 * 根据ID获取节点
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "get_node", method = RequestMethod.GET)
	@ResponseBody
	public Resource getNode(int id) {
		return resourceService.getNode(id);
	}
	

	/**
	 * 根据系统ID获取该系统的资源树
	 * @param sys_id
	 * @return
	 */
	@RequestMapping(value = "get_system_resource", method = RequestMethod.GET)
	@ResponseBody
	public List<Tree> getSystemResourceList(Long sys_id) {
		return resourceService.getSystemResourceList(sys_id);
	}
	
	/**
	 * 根据系统id以及父节点id查找对应系统下的子节点
	 * @param sys_id
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "get_system_child_resource", method = RequestMethod.GET)
	@ResponseBody
	public List<Tree> getSystemResourceList(Long sys_id,Long pid) {
		return resourceService.getSystemResourceChildList(sys_id, pid);
	}
	

	/**
	 * 加载某个树形节点的按钮列表
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "buttonListForMenu", method = RequestMethod.POST)
	@ResponseBody
	public List<Resource> getButtonTypeList(long pid) {
		return resourceService.getButtonListForMenu(pid);
	}
	
	/**
	 * 删除树形节点
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete_node", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse deleteNode(HttpServletRequest request,int id) {
		BaseResponse baseResponse = BaseResponse.getInstance();
		int num = resourceService.deleteNode(id);
		if(num>=0){
			baseResponse.setFlag(true);
		}
		return baseResponse;
	}
	
	
	/**
	 * 添加节点
	 * @param resource
	 * @return
	 */
	@RequestMapping(value = "add_node", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse addNode(Resource resource) {
		BaseResponse baseResponse = BaseResponse.getInstance();
		int num = resourceService.addNode(resource);
		if(num>=0){
			baseResponse.setFlag(true);
		}
		return baseResponse;
	}
	
	
	/**
	 * 更新树形节点信息
	 * @param resource
	 * @return
	 */
	@RequestMapping(value = "update_node", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse updateNode(Resource resource) {
		BaseResponse baseResponse = BaseResponse.getInstance();
		int num = resourceService.updateNode(resource);
		if(num>=0){
			baseResponse.setFlag(true);
		}
		return baseResponse;
	}
	
	
	/**
	 * 加载树形
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "treegrid_json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getTreeGridMap(HttpServletRequest request) {
		Map<String,Object> maps = Maps.newHashMap();
		maps.put("total", this.resourceService.getSystemTreeList().size());
		maps.put("rows", this.resourceService.getSystemTreeList());
		return maps;
	}


	@Override
	protected void setDefaultValue(HttpServletRequest request,Resource t, String operateFlag) {
		
	}
	
}                                                                                                             
