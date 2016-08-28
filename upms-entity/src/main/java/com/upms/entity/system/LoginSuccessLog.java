package com.upms.entity.system;

import java.util.Date;

import com.common.entity.BaseEntity;
import com.common.util.JsonUtil;
import com.common.util.TimeUtil;



/**
 * 
 * @author guoqiushi
 *
 */
public class LoginSuccessLog extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private long id;
    private String username;
    private String ip;
	/**
	 * 需要排序字段 暂时只能对单字段排序，以后可以改为对多列排序，此字段为数据库中的字段，非对象.
	 */
	private String orderBy = "id";
	
	private Date insertUpDate;
	
	private Date insertDownDate;
    
	public LoginSuccessLog() {
	}

	public LoginSuccessLog(long id, String username, String ip) {
		this.id = id;
		this.username = username;
		this.ip = ip;
	}
	
	

	public Date getInsertUpDate() {
		return insertUpDate;
	}

	public void setInsertUpDate(Date insertUpDate) {
		
		/**
		 * Date转化为String
		 */
		String str = TimeUtil.transferDate2String(insertUpDate, TimeUtil.formatter_type_ymd); 
		
		/**
		 * 增加当天最后时刻
		 */
		str = str+" 23:59:59";
		

		this.insertUpDate = TimeUtil.transferString2Date(str, TimeUtil.formatter_type_ymdhms);
	}

	public Date getInsertDownDate() {
		return insertDownDate;
	}

	public void setInsertDownDate(Date insertDownDate) {
		this.insertDownDate = insertDownDate;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	

	@Override
	public String toString() {
		return JsonUtil.objectToJSON(this);
	}
	
} 
