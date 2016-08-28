/**
 * 
 */
package com.upms.dao.system;

import java.io.Serializable;

import com.common.dao.BaseMapper;
import com.common.entity.BaseEntity;


/**
 * @author zhanghaiyang
 *
 */
public interface OperationLogMapper<T extends BaseEntity, ID extends Serializable>
extends BaseMapper<T, ID> {
}
