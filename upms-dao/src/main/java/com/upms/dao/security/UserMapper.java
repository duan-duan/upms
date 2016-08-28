package com.upms.dao.security;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;
import com.upms.entity.security.User;

public interface UserMapper<T extends BaseEntity, ID extends Serializable> extends BaseMapper<T, ID> {
	
	/**
	 * 做用户权限验证
	 * author：gqs
	 */
    public String validateUserPermission(@Param("suId") long suId);
    
    public int createMasEntity(User user);
    
    public int updateMasEntity(User user);
    
    public int deleteByAccount(Map<String, Object> params);

	/**
	 * @param params
	 * 更改账户激活
	 */
	public int updateById(Map<String, Object> params);
    
    /**
     * @Title: queryMasLocs 
     * @Description: 查询用户关联的站点
     * @param suAccount
     * @return List<String>
     */
    public List<String> queryMasLocs(String suAccount);

	/**
	 * @Description 查询站点下的所有有效用户
	 * @param masloc
	 * @return List<User>
	 */
	public List<User> queryUsersByMasloc(String masloc);

}
