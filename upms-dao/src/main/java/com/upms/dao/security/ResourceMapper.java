package com.upms.dao.security;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;
import com.upms.entity.security.Resource;

/**
 * 
 * @author guoqiushi
 *
 */
public interface ResourceMapper<T extends BaseEntity, ID extends Serializable> extends BaseMapper<T, ID> {
	
	List<Resource> getRootList();
	
	List<Resource> getSelectedList(@Param("account") String account);
	
	List<Resource> getSystemRootList(@Param("SYS_ID") long sys_id);
	
	List<Resource> getChildNodeList(@Param("PID") long pid);
	
	List<Resource> getChildButtonNodeList(@Param("PID") long pid);
	
	List<Resource> getSystemResourceList(@Param("SYS_ID") long sys_id);
	
	List<Resource> getSystemResourceChildList(@Param("SYS_ID") long sys_id,@Param("PID") long pid);
	
	List<Resource> getButtonListForMenu(@Param("PID") long pid);
	
	Resource getNode(@Param("ID") long id);
	
	Resource getNodeById(@Param("ID") long id);
	
	int deleteNode(@Param("ID") long id);
	
	int deleteCascade(@Param("ID") long id);
	
	int addNode(@Param("resource")Resource resource);
	
	int updateNode(@Param("resource")Resource resource);
	
	List<Resource> queryListByEntity(Resource param);
	
	List<Resource> getAuthorizedList(Map map);
	
	List<Resource> getAllMenuList(Map map);
}
