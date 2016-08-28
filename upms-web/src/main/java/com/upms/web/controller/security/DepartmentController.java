/**
 * 
 */
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
import com.upms.entity.security.Department;
import com.upms.service.api.security.DepartmentService;
import com.google.common.collect.Maps;

/**
 * 部门管理模块的Controller 
 * @author zhanghaiyang
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/department")
public class DepartmentController extends BaseCRUDAction<Department, Integer>{

	@Autowired
	DepartmentService<Department, Integer> departmentService;
	
	@Override
	public BaseService<Department, Integer> getBaseService() {
		return departmentService;
	}
	
	/**
	 * 显示部门结构树形菜单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "treegrid_json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getTreeGridMap(HttpServletRequest request) {
		Map<String,Object> maps = Maps.newHashMap();
		maps.put("total", this.departmentService.getList().size());
		maps.put("rows", this.departmentService.getList());
		return maps;
	}
	
	/**
	 * 加载树形的数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "tree_json", method = RequestMethod.GET)
	@ResponseBody
	public List<Tree> getList(HttpServletRequest request) {
		return departmentService.getList();
	}
	
	/**
	 * 获取子节点
	 * @param request
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "get_child_node", method = RequestMethod.GET)
	@ResponseBody
	public List<Tree> getChildList(HttpServletRequest request,int pid) {
		return departmentService.getChildrenList(pid);
	}
	
	
	/**
	 * 根据Id获取具体节点
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "get_node", method = RequestMethod.GET)
	@ResponseBody
	public Department getNode(int id) {
		return departmentService.getNodeById(id);
	}
	
	/**
	 * 添加节点
	 * @param department
	 * @return
	 */
	@RequestMapping(value = "add_node", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse addNode(Department department) {
		BaseResponse baseResponse = BaseResponse.getInstance();
		if(null!=department){
			long parentId = department.getDepartmentParentId();
			long departmentId = departmentService.caculateDepartmentId(parentId);
			department.setDepartmentId(departmentId);
		}
		int i= departmentService.createEntity(department);
		if(i>0){
			baseResponse.setFlag(true);
		}
		return baseResponse;
	}
	

	@Override
	protected void setDefaultValue(HttpServletRequest request,Department t, String operateFlag) {
		
	}

}
