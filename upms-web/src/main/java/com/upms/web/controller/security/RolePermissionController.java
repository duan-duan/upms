/**
 * 
 */
package com.upms.web.controller.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.model.Tree;
import com.common.service.BaseService;
import com.common.web.controller.BaseCRUDAction;
import com.upms.entity.security.Permission;
import com.upms.entity.security.RolePermission;
import com.upms.service.api.security.PermissionService;
import com.upms.service.api.security.RolePermissionService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 角色-权限表的Controller
 * @author zhanghaiyang
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/rolepermission")
public class RolePermissionController extends BaseCRUDAction<RolePermission, Long> {

	@Autowired
	PermissionService<Permission,Long> permissionService;
	
	@Autowired
	RolePermissionService<RolePermission,Long> rolePermissionService;
	
	@Override
	public BaseService<RolePermission, Long> getBaseService() {
		return rolePermissionService;
	}
	
	
	/**
	 * 加载树形
	 * @param request
	 * @param response
	 * @param srId
	 * @param sysId
	 * @return
	 */
	@RequestMapping(value = "tree_json", method = RequestMethod.POST)
	@ResponseBody
	public List<Tree> getList(HttpServletRequest request,HttpServletResponse response,Integer srId,Integer sysId) {
		List<Tree> treeList = rolePermissionService.getRolePermissionListByRoleIdAndSysId(srId,sysId);
		return treeList;
	}
	
	/**
	 * 给角色授权
	 * @param response
	 * @param idStr
	 * @param srId
	 * @return
	 */
	@RequestMapping(value = "makePermission",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> makeRolePermission(HttpServletResponse response,String idStr,Long srId){
		 Map<String, Object>  map = Maps.newHashMap();
		 Boolean success = false;
		 
		 String[] spIdArr =  idStr.split(",");
		 List<Long> spIds = new ArrayList<Long>();
		 
		 for (String spIdStr : spIdArr) {
			 spIds.add(new Long(spIdStr));
		 }
		 
		 success = rolePermissionService.storeRolePermission(srId, spIds);
		 
		 map.put("success", success);
		 
		 return map;
	}
	
	
	/**
	 * 给按钮授权
	 * @param response
	 * @param idStr
	 * @param srId
	 * @param sysId
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "makeButtonPermission",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> makeButtonRolePermission(HttpServletResponse response,String idStr,Long srId,Long sysId,Long parentId){
		 Map<String, Object>  map = Maps.newHashMap();
		 Boolean success = false;
		 
		 /**
		  * 给按钮赋权的时候，必须保证父节点已经被授权，否则操作失败 
		  */
		 List<RolePermission> queryParentPermissionList = rolePermissionService.getListByEntityAndSysId(parentId, srId, sysId);
		 
		 if((null!=queryParentPermissionList)&&(queryParentPermissionList.size()>0)){
			 
			 if((null!=sysId)&&(null!=idStr)){
				 
				 List<Permission> buttonPermissionList = permissionService.getPermissionButtonListByParentId(parentId, sysId);
				 
				 /**
				  * 删除该菜单栏的所有Button按钮权限
				  */
				 if((null!=buttonPermissionList)&&(buttonPermissionList.size()>0)){
					 for(Permission permission : buttonPermissionList){
						 Long spId = permission.getSpId();
						 
						 RolePermission rp = new RolePermission();
						 rp.setSpId(spId);
						 rp.setSrId(srId);
						 
						 rolePermissionService.deleteByObject(rp);
					 }
				 }
					 if(!"".equals(idStr.trim())){//idStr:以","号分隔开的SP_ID字符串
							String[] spIdArr = null;
							 if(null!=parentId){//有按钮授权的时候，需要将它们的父节点也授权
								Permission permission =  permissionService.queryById(parentId);
								if(permission==null){//如果父节点之前没有被赋权，则添加到此次授权中
									 idStr = idStr+","+parentId;
								}
							 }
							 
							 spIdArr = idStr.split(",");
							
							if((null!=spIdArr)&&(spIdArr.length>0)){
								for(String spIdStr : spIdArr){
									if(!"".equals(spIdStr)){
										saveRolePermission(Long.parseLong(spIdStr),Long.parseLong(srId.toString()));
									}
								}
							}
				 }
					 success = true; 
			 }
		 }else{
			 map.put("msg", "要授权按钮的菜单项未授权！");
		 }
		 
		
		 
		 map.put("success", success);
		 
		 return map;
	}
	
	/**
	 * 遍历角色权限列表，获取默认的ＳＰＩＤ
	 * @param checkedRolePermissionList
	 * @return
	 */
	private List<String> getDefaultSpIdStrFromList(List<RolePermission> checkedRolePermissionList){
		List<String> resultList = Lists.newArrayList();
		
		if((null!=checkedRolePermissionList)&&(checkedRolePermissionList.size()>0)){
			for(RolePermission rolePermission : checkedRolePermissionList){
				resultList.add(rolePermission.getSpId()+"");
			}
		}
		
		return resultList;
	}



	@Override
	protected void setDefaultValue(HttpServletRequest request,RolePermission t, String operateFlag) {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * 向数据库中追加权限数据
	 * 
	 * @param spId
	 * @param srId
	 */
	private void saveRolePermission(long spId,long srId){
		RolePermission rolePermission = new RolePermission();
		rolePermission.setSpId(spId);
		rolePermission.setSrId(srId);
		//数据库中不存在时候才插入
		List<RolePermission> queryList = rolePermissionService.queryListByEntity(rolePermission);
		if((null==queryList)||(queryList.size()<=0)){
			rolePermissionService.createEntity(rolePermission);//添加新的授权信息
		}
	}
	
	
}
