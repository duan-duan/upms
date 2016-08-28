package com.upms.service.api.security;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.common.entity.BaseEntity;
import com.common.service.BaseService;
import com.upms.entity.security.User;

public interface UserService<T extends BaseEntity, ID extends Serializable> extends BaseService<T, ID> {
	
	public User initLoginUserInfo(User adminUser);
	
	public Map<String,Object> validateLoginUser(User adminUser);
	
	/**
	 * 自提收货 用户权限验证
	 * author：gqs
	 */
    public String validateUserPermission(long suId);
    
    public int createUser(User user);
    
    public int updateUser(User user);
    
    /**
     * 更改账户激活
     * @param ids
     * wangzaifei
     */
    public int updateById(Integer ids[]);
    
    /**
     * @Title: queryMasLocs 
     * @Description: 查询用户关联的站点
     * @param suAccount
     * @return List<String>
     */
    public List<String> queryMasLocs(String suAccount);

    /**
     * 根据masloc取所有用户
     * @param masloc
     * @return
     */
    public List<User> getUsersByMasloc(String masloc);

}
