package com.es.ssp.query;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

import cn.org.rapid_framework.page.PageRequest;


/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 */
public class LoginUserQuery extends PageRequest implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 用户id */
	private Integer userId;
	/** 用户名 */
	private String userName;
	/** 真实姓名 */
	private String realName;
	/** 联系方式 */
	private String mobile;
	/** 角色类型 */
	private Integer roleType;
	/** 密码 */
	private String psw;
	/** 创建时间 */
	private Long createTime;
	/** 状态 */
	private Integer status;

	public Integer getUserId() {
		return this.userId;
	}
	
	public void setUserId(Integer value) {
		this.userId = value;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String value) {
		this.userName = value;
	}
	
	public String getRealName() {
		return this.realName;
	}
	
	public void setRealName(String value) {
		this.realName = value;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	public Integer getRoleType() {
		return this.roleType;
	}
	
	public void setRoleType(Integer value) {
		this.roleType = value;
	}
	
	public String getPsw() {
		return this.psw;
	}
	
	public void setPsw(String value) {
		this.psw = value;
	}
	
	public Long getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Long value) {
		this.createTime = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

