package com.upms.entity.security;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.common.entity.BaseEntity;
import com.upms.vo.user.PermissionVo;

public class User extends BaseEntity {

	private static final long serialVersionUID = 6337475073048287298L;

	/**
	 * Id号
	 */
	private Long id;

	/**
	 * 账号
	 */
	private String account;

	/**
	 * 用户名
	 */
	private String name;

	/**
	 * email
	 */
	private String email;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 确认密码
	 */
	private String repassword;

	/**
	 * 是否是管理员
	 */
	private String adminFlag;

	/**
	 * 仓库
	 */
	private String masLoc;
	
	/**
	 * 关联站点列表
	 */
	private List<String> masLocList;

	/**
	 * 需要排序字段 暂时只能对单字段排序，以后可以改为对多列排序，此字段为数据库中的字段，非对象.
	 */
	private String orderBy = "su_id";

	private String extension;
	private String segment;
	private String active;
	private String remark;
	private String lspCode;
	private String domain;
	private String roles;
	private String title;
	private String isEmployee;
	private String managerEmail;
	private Long enabled;
	private String timezone;
	private String language;
	private Long defaultBuid;
	private String countryCode;
	private String defaultAreaCode;
	private Date lastLoginDate;
	private String hasChangedPswd;
	private Long invalidAttampts;
	private Date lockoutTime;
	private Long rfLoginCount;
	private String loginStatus;
	private String telephoneNum;
	private Long sex;
	private Date effectDate;
	private Date expireDate;
	private String employeeNo;

	/**
	 * 系统id
	 */
	private long sysId;

	private Set<PermissionVo> permList;

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getLockoutTime() {
		return lockoutTime;
	}

	public void setLockoutTime(Date lockoutTime) {
		this.lockoutTime = lockoutTime;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLspCode() {
		return lspCode;
	}

	public void setLspCode(String lspCode) {
		this.lspCode = lspCode;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsEmployee() {
		return isEmployee;
	}

	public void setIsEmployee(String isEmployee) {
		this.isEmployee = isEmployee;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public Long getEnabled() {
		return enabled;
	}

	public void setEnabled(Long enabled) {
		this.enabled = enabled;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Long getDefaultBuid() {
		return defaultBuid;
	}

	public void setDefaultBuid(Long defaultBuid) {
		this.defaultBuid = defaultBuid;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getDefaultAreaCode() {
		return defaultAreaCode;
	}

	public void setDefaultAreaCode(String defaultAreaCode) {
		this.defaultAreaCode = defaultAreaCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(String adminFlag) {
		this.adminFlag = adminFlag;
	}

	public String getMasLoc() {
		if (masLocList != null && masLocList.size() != 0) {
			masLoc = this.masLocList.toString();
		}
		return masLoc;
	}
	public void setMasLoc(String masLoc) {
		this.masLoc = masLoc;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getRepassword() {
		if (StringUtils.isEmpty(repassword)) {
			return getPassword();
		} else {
			return repassword;
		}
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getHasChangedPswd() {
		return hasChangedPswd;
	}

	public void setHasChangedPswd(String hasChangedPswd) {
		this.hasChangedPswd = hasChangedPswd;
	}

	public Long getInvalidAttampts() {
		return invalidAttampts;
	}

	public void setInvalidAttampts(Long invalidAttampts) {
		this.invalidAttampts = invalidAttampts;
	}

	public Long getRfLoginCount() {
		return rfLoginCount;
	}

	public void setRfLoginCount(Long rfLoginCount) {
		this.rfLoginCount = rfLoginCount;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getTelephoneNum() {
		return telephoneNum;
	}

	public void setTelephoneNum(String telephoneNum) {
		this.telephoneNum = telephoneNum;
	}

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public Set<PermissionVo> getPermList() {
		return permList;
	}

	public void setPermList(Set<PermissionVo> permList) {
		this.permList = permList;
	}

	public Set<String> getPerms() {
		Set<PermissionVo> permissionVoSet = getPermList();
		if (permissionVoSet == null) {
			return null;
		}
		Set<String> allPerms = new HashSet<String>();
		for (PermissionVo permissionVo : permissionVoSet) {
			if (StringUtils.isNotEmpty(permissionVo.getHref())) {
				allPerms.add(permissionVo.getHref());
			}
		}
		return allPerms;
	}

	/**
	 * @return the masLocList
	 */
	public List<String> getMasLocList() {
		return masLocList;
	}

	/**
	 * @param masLocList the masLocList to set
	 */
	public void setMasLocList(List<String> masLocList) {
		this.masLocList = masLocList;
	}
	
}
