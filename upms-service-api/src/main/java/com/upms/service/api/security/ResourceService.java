package com.upms.service.api.security;


import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.entity.BaseEntity;
import com.common.model.Tree;
import com.common.service.BaseService;
import com.upms.entity.security.Resource;

public interface ResourceService <T extends BaseEntity, ID extends Serializable> extends BaseService<T, ID> {
	
	/**
	 * 获取资源表中所有节点列表
	 * @return
	 */
	public List<Tree> getList();
	
	/**
	 * 获取资源表中所有节点列表(为特定的登录用户)
	 * @return
	 */
	public List<Tree> getListForUser(String account,long sysId);
	
	/**
	 * 根据pid获取所有子节点列表
	 * @param pid
	 * @return
	 */
	public List<Tree> getChildList(long pid);
	
	/**
	 * 根据pid获取所有子节点按钮节点列表
	 * @param pid
	 * @return
	 */
	public List<Tree> getChildButtonList(long pid);
	
	/**
	 * 根据sys_id 获取某系统下的资源根节点列表
	 * @param sys_id
	 * @return
	 */
	public List<Tree> getSystemResourceList(long sys_id);
	
	/**
	 * 根据系统ID和父节点id查找子节点
	 * author:gqs
	 * @param sys_id
	 * @param pid
	 * @return
	 */
	public List<Tree> getSystemResourceChildList(@Param("sys_id") long sys_id,@Param("pid") long pid);
	 
	/**
	 * 获取操作类的所有资源列表
	 * @return
	 */
	public List<Resource> getButtonListForMenu(long pid);
	
	/**
	 * 根据id获取所有非操作类资源列表
	 * @param id
	 * @return
	 */
	public Resource getNode(long id);
	
	/**
	 * 根据id级联删除节点
	 * @param id
	 * @return
	 */
	public int deleteNode(long id);
	
	/**
	 * 添加节点
	 * @param resource
	 * @return
	 */
	public int addNode(Resource resource);
	
	/**
	 * 更新节点
	 * @param resource
	 * @return
	 */
	public int updateNode(Resource resource);
	
	/**
	 * 获取系统和资源树状列表
	 * @return
	 */
	public List<Tree> getSystemTreeList();
}
