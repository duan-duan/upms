/**
 * 
 */
package com.upms.dao.security;

import java.io.Serializable;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;

/**
 * @author zhanghaiyang
 *
 */
public interface SystemModelMapper <T extends BaseEntity, ID extends Serializable>
extends BaseMapper<T, ID> {
	
}
